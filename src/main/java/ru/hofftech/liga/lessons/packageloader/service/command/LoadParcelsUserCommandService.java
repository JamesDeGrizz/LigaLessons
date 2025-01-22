package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.BillingService;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportTruckService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.OnePerTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.validator.LoadParcelsUserCommandValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для загрузки посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд загрузки посылок.
 */
@Slf4j
@AllArgsConstructor
public class LoadParcelsUserCommandService implements UserCommandService {
    private static final String PARCELS_DELIMITER = ",";
    private static final String TRUCKS_DELIMITER = ",";
    private static final String TRUCKS_SIZE_DELIMITER = "x";

    private final FileLoaderService fileLoaderService;
    private final ReportTruckService reportTruckService;
    private final ParcelRepository parcelRepository;
    private final LoadParcelsUserCommandValidator commandValidator;
    private final ApplicationContext applicationContext;
    private final BillingService billingService;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылки не могут быть погружены: \nПередан пустой список аргументов";
        }
        if (!(command instanceof LoadParcelsUserCommandDto)) {
            return "Посылки не могут быть погружены: \nПередана команда неправильного типа";
        }

        var castedCommand = (LoadParcelsUserCommandDto) command;

        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылки не могут быть погружены: \n" + String.join("\n", validationErrors);
        }

        var algorithm = getPlacingAlgorithm(castedCommand.type());
        var truckSizes = getTruckSizes(castedCommand.trucks());
        var needToSaveReport = castedCommand.outFilename() != null && !castedCommand.outFilename().isEmpty();

        var errors = new ArrayList<String>();
        var packages = getPackages(castedCommand, errors);
        if (!errors.isEmpty()) {
            return "Посылки не могут быть погружены: \n" + String.join("\n", errors);
        }

        var trucks = placePackagesToTrucks(packages, algorithm, truckSizes, errors);
        if (!errors.isEmpty()) {
            return "Посылки не могут быть погружены: \n" + String.join("\n", errors);
        }

        reportTruckContent(trucks);
        saveReportToFileIfNeed(needToSaveReport, castedCommand.outFilename(), trucks);

        billingService.saveOrder(castedCommand.userId(), Command.LOAD_PARCELS, trucks.size(), packages);

        return "Посылки успешно погружены";
    }

    private List<Parcel> getPackages(LoadParcelsUserCommandDto command, List<String> errors) {
        var packages = new ArrayList<Parcel>();
        String[] packageNames = null;

        if (command.parcelsText() != null && !command.parcelsText().isEmpty()) {
            packageNames = command.parcelsText().split(PARCELS_DELIMITER);
        }
        else if (command.parcelsFile() != null && !command.parcelsFile().isEmpty()) {
            packageNames = fileLoaderService.getParcelNames(command.parcelsFile());
        }

        if (packageNames == null) {
            errors.add("Не удалось загрузить посылки");
            return Collections.emptyList();
        }

        for (var packageName : packageNames) {
            var found = parcelRepository.find(packageName);
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

    private PlacingAlgorithm getPlacingAlgorithm(String algorithmString) {
        var algorithm = Integer.parseInt(algorithmString);
        return PlacingAlgorithm.valueOf(algorithm);
    }

    private List<TruckSize> getTruckSizes(String trucks) {
        var truckSizeList = trucks.split(TRUCKS_DELIMITER);

        var result = new ArrayList<TruckSize>();
        for (var truckSize : truckSizeList) {
            var widthAndHeight = truckSize.split(TRUCKS_SIZE_DELIMITER);
            var width = Integer.parseInt(widthAndHeight[0]);
            var height = Integer.parseInt(widthAndHeight[1]);
            result.add(new TruckSize(width, height));
        }

        return result;
    }

    private List<Truck> placePackagesToTrucks(List<Parcel> parcels, PlacingAlgorithm algorithm, List<TruckSize> truckSizes, List<String> errors) {
        List<Truck> trucks;
        try {
            var logisticService = switch (algorithm) {
                case ONE_PER_TRUCK -> applicationContext.getBean(OnePerTruckLogisticService.class);
                case FILL_TRUCK -> applicationContext.getBean(FullFillTruckLogisticService.class);
                case BALANCED -> applicationContext.getBean(BalancedFillTruckLogisticService.class);
                case NONE_OF -> throw new IllegalArgumentException("Не выбран ни один алгоритм");

            };
            trucks = logisticService.placeParcelsToTrucks(parcels, truckSizes);
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
