package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.factory.LogisticServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

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
    private static final String ARGUMENT_OUT_FILENAME = "-out-filename";
    private static final String ARGUMENT_PACKAGES_TEXT = "-parcels-text";
    private static final String ARGUMENT_PACKAGES_FILE = "-parcels-file";
    private static final String ARGUMENT_ALGORITHM_TYPE = "-type";
    private static final String ARGUMENT_TRUCKS = "-trucks";

    private final FileLoaderService fileLoaderService;
    private final ReportService reportService;
    private final LogisticServiceFactory logisticServiceFactory;
    private final PackageRepository packageRepository;
    private final List<String> errors = new ArrayList<>();

    /**
     * Выполняет команду загрузки посылок на основе переданных аргументов.
     *
     * @param arguments аргументы команды
     * @return сообщение о результате выполнения команды
     */
    @Override
    public String execute(Map<String, String> arguments) {
        var algorithm = getPlacingAlgorithm(arguments);
        var truckSizes = getTruckSizes(arguments);
        var needToSaveReport = getNeedSaveToFile(arguments);
        var reportFileName = getReportFileName(arguments, needToSaveReport);
        var packages = getPackages(arguments);
        if (!errors.isEmpty()) {
            var errorMessage = "Посылки не могут быть погружены: \n" + String.join("\n", errors);
            errors.clear();
            return errorMessage;
        }

        var trucks = placePackagesToTrucks(packages, algorithm, truckSizes);
        if (!errors.isEmpty()) {
            var errorMessage = "Посылки не могут быть погружены: \n" + String.join("\n", errors);
            errors.clear();
            return errorMessage;
        }

        reportTruckContent(trucks);

        saveReportToFileIfNeed(needToSaveReport, reportFileName, trucks);

        return "Посылки успешно погружены";
    }

    private boolean getNeedSaveToFile(Map<String, String> arguments) {
        if (!arguments.containsKey(ARGUMENT_OUT_TYPE)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_OUT_TYPE + "\"");
            return false;
        }

        return arguments.get(ARGUMENT_OUT_TYPE).equals("json-file");
    }

    private String getReportFileName(Map<String, String> arguments, boolean needToSaveReport) {
        if (!needToSaveReport) {
            return null;
        }

        if (!arguments.containsKey(ARGUMENT_OUT_FILENAME)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_OUT_FILENAME + "\"");
            return null;
        }

        return arguments.get(ARGUMENT_OUT_FILENAME);
    }

    private List<Package> getPackages(Map<String, String> arguments) {
        var packages = new ArrayList<Package>();
        String[] packageNames = null;

        if (arguments.containsKey(ARGUMENT_PACKAGES_TEXT)) {
            packageNames = arguments.get(ARGUMENT_PACKAGES_TEXT).split("\\\\n");
        }
        else if (arguments.containsKey(ARGUMENT_PACKAGES_FILE)) {
            packageNames = fileLoaderService.getPackageNames(arguments.get(ARGUMENT_PACKAGES_FILE));
        }
        else {
            errors.add("Не хватает аргументов \"" + ARGUMENT_PACKAGES_TEXT + "\" или \"" + ARGUMENT_PACKAGES_FILE + "\"");
            return Collections.emptyList();
        }

        if (packageNames == null) {
            errors.add("Не удалось загрузить посылки");
            return Collections.emptyList();
        }

        for (var packageName : packageNames) {
            var found = packageRepository.find(packageName);
            if (found == null) {
                errors.add("Посылки с названием \"" + packageName + "\" не существует");
                continue;
            }
            packages.add(found);
        }

        return packages.stream()
                .sorted((p1, p2) -> p1.getWidth() * p1.getHeight() - p2.getWidth() * p2.getHeight())
                .collect(Collectors.toUnmodifiableList());
    }

    private PlacingAlgorithm getPlacingAlgorithm(Map<String, String> arguments) {
        if (!arguments.containsKey(ARGUMENT_ALGORITHM_TYPE)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_ALGORITHM_TYPE + "\"");
            return PlacingAlgorithm.NoneOf;
        }

        var algorithmString = arguments.get(ARGUMENT_ALGORITHM_TYPE);
        try {
            var algorithm = Integer.parseInt(algorithmString);

            if (algorithm < 0 || algorithm > 2) {
                errors.add("Неправильное значение типа алгоритма: " + algorithmString);
                return PlacingAlgorithm.NoneOf;
            }

            return PlacingAlgorithm.valueOf(algorithm);
        }
        catch (Exception e) {
            errors.add("Введённое значение нельзя привести к числу: " + algorithmString);
            return PlacingAlgorithm.NoneOf;
        }
    }

    private List<TruckSize> getTruckSizes(Map<String, String> arguments) {
        if (!arguments.containsKey(ARGUMENT_TRUCKS)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_TRUCKS + "\"");
            return Collections.emptyList();
        }

        var truckSizeList = arguments.get(ARGUMENT_TRUCKS).split("\\\\n");

        var result = new ArrayList<TruckSize>();
        for (var truckSize : truckSizeList) {
            var widthAndHeight = truckSize.split("x");
            if (widthAndHeight.length != 2) {
                errors.add("Неправильный формат размера кузова грузовика: " + truckSize);
                continue;
            }

            try {
                var width = Integer.parseInt(widthAndHeight[0]);
                var height = Integer.parseInt(widthAndHeight[1]);

                if (width < 0 || width > TruckSize.TRUCK_MAX_WIDTH) {
                    errors.add("Неправильное значение ширины грузовика: " + width);
                    continue;
                }

                if (height < 0 || height > TruckSize.TRUCK_MAX_HEIGHT) {
                    errors.add("Неправильное значение высоты грузовика: " + height);
                    continue;
                }

                result.add(new TruckSize(width, height));
            }
            catch (Exception e) {
                errors.add("Неправильный формат размера кузова грузовика: " + truckSize);
            }
        }

        return result;
    }

    private List<Truck> placePackagesToTrucks(List<Package> packages, PlacingAlgorithm algorithm, List<TruckSize> truckSizes) {
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
            reportService.reportTruckContent(truck.getContent());
        }
    }

    private void saveReportToFileIfNeed(boolean needToSaveReport, String reportFileName, List<Truck> trucks) {
        if (needToSaveReport) {
            reportService.saveTrucksToFile(reportFileName, trucks);
        }
    }
}
