package ru.hofftech.liga.lessons.parcelloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.parcelloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.parcelloader.model.Parcel;
import ru.hofftech.liga.lessons.parcelloader.model.Truck;
import ru.hofftech.liga.lessons.parcelloader.model.TruckSize;
import ru.hofftech.liga.lessons.parcelloader.model.dto.LoadParcelsResponseDto;
import ru.hofftech.liga.lessons.parcelloader.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.parcelloader.model.entity.OrderOutbox;
import ru.hofftech.liga.lessons.parcelloader.model.entity.OrderOutboxId;
import ru.hofftech.liga.lessons.parcelloader.model.enums.Command;
import ru.hofftech.liga.lessons.parcelloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.parcelloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.parcelloader.service.logistic.LogisticAlgorithmResolver;
import ru.hofftech.liga.lessons.parcelloader.validator.LoadParcelsUserCommandValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для загрузки посылок на основе команд пользователя.
 */
@Slf4j
@RequiredArgsConstructor
public class LoadParcelsService {
    private static final String PARCELS_DELIMITER = ",";
    private static final String TRUCKS_DELIMITER = ",";
    private static final String TRUCKS_SIZE_DELIMITER = "x";
    private static final int TRUCKS_SIZE_WIDTH_INDEX = 0;
    private static final int TRUCKS_SIZE_HEIGHT_INDEX = 1;

    private final FileLoaderService fileLoaderService;
    private final ReportTruckService reportTruckService;
    private final ParcelRepository parcelRepository;
    private final LoadParcelsUserCommandValidator commandValidator;
    private final LogisticAlgorithmResolver logisticAlgorithmResolver;
    private final ParcelMapper parcelMapper;
    private final OrderRepository orderRepository;

    public LoadParcelsResponseDto execute(LoadParcelsUserCommandDto command) {
        if (command == null) {
            throw new IllegalArgumentException("Посылки не могут быть погружены: \nПередан пустой список аргументов");
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Посылки не могут быть погружены: \n" + String.join("\n", validationErrors));
        }

        var truckSizes = getTruckSizes(command.trucks());
        var needToSaveReport = command.outFilename() != null && !command.outFilename().isEmpty();

        var errors = new ArrayList<String>();
        var parcels = getParcels(command, errors);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Посылки не могут быть погружены: \n" + String.join("\n", errors));
        }

        var trucks = placePackagesToTrucks(parcels, command.type(), truckSizes, errors);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Посылки не могут быть погружены: \n" + String.join("\n", errors));
        }

        reportTruckContent(trucks);
        saveReportToFileIfNeed(needToSaveReport, command.outFilename(), trucks);

        orderRepository.save(
                OrderOutbox.builder()
                        .id(OrderOutboxId.builder()
                                .name(command.userId())
                                .date(new Date())
                                .operation(Command.LOAD_PARCELS.toString())
                                .build())
                        .trucksCount(trucks.size())
                        .parcelsCount(parcels.size())
                        .cellsCount(parcels.stream()
                                .mapToInt(parcel -> parcel.getSize())
                                .sum())
                .build());

        return new LoadParcelsResponseDto("Посылки успешно погружены");
    }

    private List<Parcel> getParcels(LoadParcelsUserCommandDto command, List<String> errors) {
        var parcels = new ArrayList<Parcel>();
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
            var found = parcelRepository.findByName(packageName);
            if (found.isEmpty()) {
                errors.add("Посылки с названием \"" + packageName + "\" не существует");
                continue;
            }
            parcels.add(parcelMapper.toParcel(found.get()));
        }

        return parcels.stream()
                .sorted((p1, p2) -> p1.getWidth() * p1.getHeight() - p2.getWidth() * p2.getHeight())
                .collect(Collectors.toList());
    }

    private List<TruckSize> getTruckSizes(String trucks) {
        var truckSizeList = trucks.split(TRUCKS_DELIMITER);

        var result = new ArrayList<TruckSize>();
        for (var truckSize : truckSizeList) {
            var widthAndHeight = truckSize.split(TRUCKS_SIZE_DELIMITER);
            var width = Integer.parseInt(widthAndHeight[TRUCKS_SIZE_WIDTH_INDEX]);
            var height = Integer.parseInt(widthAndHeight[TRUCKS_SIZE_HEIGHT_INDEX]);
            result.add(new TruckSize(width, height));
        }

        return result;
    }

    private List<Truck> placePackagesToTrucks(List<Parcel> parcels, String algorithm, List<TruckSize> truckSizes, List<String> errors) {
        List<Truck> trucks;
        try {
            var logisticService = logisticAlgorithmResolver.getLogisticService(algorithm);
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
