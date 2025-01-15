package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.enums.UnloadCommandFlag;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportPackageService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.UnloadTrucksUserCommandValidator;

import java.util.ArrayList;
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
    private final UnloadTrucksUserCommandValidator commandValidator;

    /**
     * Выполняет команду разгрузки грузовиков на основе переданных аргументов.
     *
     * @param arguments аргументы команды
     * @return сообщение о результате выполнения команды
     */
    @Override
    public String execute(Map<String, String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            return "Посылки не могут быть разгружены: \nПередан пустой список аргументов";
        }
        var validationErrors = commandValidator.validate(arguments);
        if (!validationErrors.isEmpty()) {
            return "Посылки не могут быть разгружены: \n" + String.join("\n", validationErrors);
        }

        var reportFileName = getReportFileName(arguments);
        var withCount = getWithCount(arguments);
        var trucks = getTrucks(arguments);
        if (trucks.isEmpty()) {
            return "Не удалось загрузить грузовики из файла";
        }

        var packages = getPackagesFromTrucks(trucks);

        reportPackageService.reportPackages(packages, withCount);
        reportPackageService.savePackagesToFile(reportFileName, packages, withCount);

        return "Посылки успешно погружены";
    }

    private String getReportFileName(Map<String, String> arguments) {
        return arguments.get(ARGUMENT_OUT_FILE);
    }

    private List<Truck> getTrucks(Map<String, String> arguments) {
        var trucksSourceFileName = arguments.get(ARGUMENT_IN_FILE);
        return fileLoaderService.getTrucks(trucksSourceFileName);
    }

    private UnloadCommandFlag getWithCount(Map<String, String> arguments) {
        return arguments.containsKey(ARGUMENT_WITH_COUNT) ? UnloadCommandFlag.WITH_COUNT : UnloadCommandFlag.WITHOUT_COUNT;
    }

    private List<Package> getPackagesFromTrucks(List<Truck> trucks) {
        var packages = new ArrayList<Package>();
        trucks.forEach(truck -> {
            packages.addAll(truck.getPackages());
        });
        return packages;
    }
}
