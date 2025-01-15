package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.enums.UnloadCommandFlag;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportPackageService;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Сервис для разгрузки грузовиков на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд разгрузки грузовиков.
 */
@AllArgsConstructor
public class UnloadTrucksUserCommandService implements UserCommandService {
    private static final String ARGUMENT_IN_FILE = "-infile";
    private static final String ARGUMENT_OUT_FILE = "-outfile";
    private static final String ARGUMENT_WITH_COUNT = "--withcount";

    private final FileLoaderService fileLoaderService;
    private final ReportPackageService reportPackageService;
    private final TruckServiceFactory truckServiceFactory;

    private final List<String> errors = new ArrayList<>();

    /**
     * Выполняет команду разгрузки грузовиков на основе переданных аргументов.
     *
     * @param arguments аргументы команды
     * @return сообщение о результате выполнения команды
     */
    @Override
    public String execute(Map<String, String> arguments) {
        var reportFileName = getReportFileName(arguments);
        var trucks = getTrucks(arguments);
        var withCount = getWithCount(arguments);
        if (!errors.isEmpty()) {
            var errorMessage = "Посылки не могут быть погружены: \n" + String.join("\n", errors);
            errors.clear();
            return errorMessage;
        }

        var packages = getPackagesFromTrucks(trucks);

        reportPackageService.reportPackages(packages, withCount);
        reportPackageService.savePackagesToFile(reportFileName, packages, withCount);

        return "Посылки успешно погружены";
    }

    private String getReportFileName(Map<String, String> arguments) {
        if (!arguments.containsKey(ARGUMENT_OUT_FILE)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_OUT_FILE + "\"");
            return null;
        }

        return arguments.get(ARGUMENT_OUT_FILE);
    }

    private List<Truck> getTrucks(Map<String, String> arguments) {
        if (!arguments.containsKey(ARGUMENT_IN_FILE)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_IN_FILE + "\"");
            return Collections.emptyList();
        }

        var trucksSourceFileName = arguments.get(ARGUMENT_IN_FILE);
        var trucks = fileLoaderService.getTrucks(trucksSourceFileName);
        if (trucks.isEmpty()) {
            errors.add("Не удалось загрузить грузовики из файла " + trucksSourceFileName);
            return Collections.emptyList();
        }

        return trucks;
    }

    private UnloadCommandFlag getWithCount(Map<String, String> arguments) {
        return arguments.containsKey(ARGUMENT_WITH_COUNT) ? UnloadCommandFlag.WITH_COUNT : UnloadCommandFlag.WITHOUT_COUNT;
    }

    private List<Package> getPackagesFromTrucks(List<Truck> trucks) {
        var packages = new ArrayList<Package>();
        for (var truck : trucks) {
            var truckService = truckServiceFactory.getTruckService(truck);
            packages.addAll(truckService.getPackages());
        }
        return packages;
    }
}
