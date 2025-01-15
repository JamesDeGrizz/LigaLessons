package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportTruckService;
import ru.hofftech.liga.lessons.packageloader.service.factory.LogisticServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.LoadPackagesUserCommandValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис для загрузки посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд загрузки посылок.
 */
@Slf4j
@AllArgsConstructor
public class LoadPackagesUserCommandService implements UserCommandService {
    private static final String ARGUMENT_OUT_TYPE = "-out";
    private static final String ARGUMENT_OUT_TYPE_JSON = "json-file";
    private static final String ARGUMENT_OUT_FILENAME = "-out-filename";
    private static final String ARGUMENT_PACKAGES_TEXT = "-parcels-text";
    private static final String ARGUMENT_PACKAGES_FILE = "-parcels-file";
    private static final String ARGUMENT_ALGORITHM_TYPE = "-type";
    private static final String ARGUMENT_TRUCKS = "-trucks";

    private final FileLoaderService fileLoaderService;
    private final ReportTruckService reportTruckService;
    private final LogisticServiceFactory logisticServiceFactory;
    private final PackageRepository packageRepository;
    private final LoadPackagesUserCommandValidator commandValidator;

    /**
     * Выполняет команду загрузки посылок на основе переданных аргументов.
     *
     * @param arguments аргументы команды
     * @return сообщение о результате выполнения команды
     */
    @Override
    public String execute(Map<String, String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            return "Посылки не могут быть погружены: \nПередан пустой список аргументов";
        }
        var validationErrors = commandValidator.validate(arguments);
        if (!validationErrors.isEmpty()) {
            return "Посылки не могут быть погружены: \n" + String.join("\n", validationErrors);
        }

        var algorithm = getPlacingAlgorithm(arguments);
        var truckSizes = getTruckSizes(arguments);
        var needToSaveReport = getNeedSaveToFile(arguments);
        var reportFileName = getReportFileName(arguments, needToSaveReport);

        var errors = new ArrayList<String>();
        var packages = getPackages(arguments, errors);
        if (!errors.isEmpty()) {
            return "Посылки не могут быть погружены: \n" + String.join("\n", errors);
        }

        var trucks = placePackagesToTrucks(packages, algorithm, truckSizes, errors);
        if (!errors.isEmpty()) {
            return "Посылки не могут быть погружены: \n" + String.join("\n", errors);
        }

        reportTruckContent(trucks);
        saveReportToFileIfNeed(needToSaveReport, reportFileName, trucks);

        return "Посылки успешно погружены";
    }

    private boolean getNeedSaveToFile(Map<String, String> arguments) {
        return arguments.get(ARGUMENT_OUT_TYPE)
                .equals(ARGUMENT_OUT_TYPE_JSON);
    }

    private String getReportFileName(Map<String, String> arguments, boolean needToSaveReport) {
        if (!needToSaveReport) {
            return null;
        }

        return arguments.get(ARGUMENT_OUT_FILENAME);
    }

    private List<Package> getPackages(Map<String, String> arguments, List<String> errors) {
        var packages = new ArrayList<Package>();
        String[] packageNames = null;

        if (arguments.containsKey(ARGUMENT_PACKAGES_TEXT)) {
            packageNames = arguments.get(ARGUMENT_PACKAGES_TEXT).split("\\\\n");
        }
        else if (arguments.containsKey(ARGUMENT_PACKAGES_FILE)) {
            packageNames = fileLoaderService.getPackageNames(arguments.get(ARGUMENT_PACKAGES_FILE));
        }

        if (packageNames == null) {
            errors.add("Не удалось загрузить посылки");
            return Collections.emptyList();
        }

        for (var packageName : packageNames) {
            var found = packageRepository.find(packageName);
            if (!found.isPresent()) {
                errors.add("Посылки с названием \"" + packageName + "\" не существует");
                continue;
            }
            packages.add(found.get());
        }

        return packages.stream()
                .sorted((p1, p2) -> p1.getWidth() * p1.getHeight() - p2.getWidth() * p2.getHeight())
                .collect(Collectors.toList());
    }

    private PlacingAlgorithm getPlacingAlgorithm(Map<String, String> arguments) {
        var algorithmString = arguments.get(ARGUMENT_ALGORITHM_TYPE);
        var algorithm = Integer.parseInt(algorithmString);
        return PlacingAlgorithm.valueOf(algorithm);
    }

    private List<TruckSize> getTruckSizes(Map<String, String> arguments) {
        var truckSizeList = arguments.get(ARGUMENT_TRUCKS).split("\\\\n");

        var result = new ArrayList<TruckSize>();
        for (var truckSize : truckSizeList) {
            var widthAndHeight = truckSize.split("x");
            var width = Integer.parseInt(widthAndHeight[0]);
            var height = Integer.parseInt(widthAndHeight[1]);
            result.add(new TruckSize(width, height));
        }

        return result;
    }

    private List<Truck> placePackagesToTrucks(List<Package> packages, PlacingAlgorithm algorithm, List<TruckSize> truckSizes, List<String> errors) {
        List<Truck> trucks;
        try {
            var logisticService = logisticServiceFactory.getLogisticService(algorithm);
            trucks = logisticService.placePackagesToTrucks(packages, truckSizes);
        } catch (Exception e) {
            errors.add(e.getMessage());
            return Collections.emptyList();
        }

        return trucks;
    }

    private void reportTruckContent(List<Truck> trucks) {
        log.debug("Получено {} грузовиков", trucks.size());
        for (var truck : trucks) {
            reportTruckService.reportTruckContent(truck.getContent());
        }
    }

    private void saveReportToFileIfNeed(boolean needToSaveReport, String reportFileName, List<Truck> trucks) {
        if (needToSaveReport) {
            reportTruckService.saveTrucksToFile(reportFileName, trucks);
        }
    }
}
