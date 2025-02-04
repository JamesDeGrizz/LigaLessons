package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.RequiredArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.config.TopicsConfiguration;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.dto.OrderDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.KafkaSenderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportParcelService;
import ru.hofftech.liga.lessons.packageloader.validator.UnloadTrucksUserCommandValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Сервис для разгрузки грузовиков на основе команд пользователя.
 */
@RequiredArgsConstructor
public class UnloadTrucksUserCommandService {
    private final FileLoaderService fileLoaderService;
    private final ReportParcelService reportParcelService;
    private final UnloadTrucksUserCommandValidator commandValidator;
    private final KafkaSenderService kafkaSenderService;
    private final TopicsConfiguration topicsConfiguration;

    public String execute(UnloadTrucksUserCommandDto command) {
        if (command == null) {
            return "Посылки не могут быть разгружены: \nПередан пустой список аргументов";
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            return "Посылки не могут быть разгружены: \n" + String.join("\n", validationErrors);
        }

        var trucks = fileLoaderService.getTrucks(command.infile());
        if (trucks.isEmpty()) {
            return "Не удалось загрузить грузовики из файла";
        }

        var parcels = getParcelsFromTrucks(trucks);

        reportParcelService.reportParcels(parcels, command.withCount());
        reportParcelService.saveParcelsToFile(command.outfile(), parcels, command.withCount());

        sendNewOrder(parcels, trucks.size(), command);

        return "Посылки успешно разгружены";
    }

    private List<Parcel> getParcelsFromTrucks(List<Truck> trucks) {
        var packages = new ArrayList<Parcel>();
        trucks.forEach(truck -> {
            packages.addAll(truck.getParcels());
        });
        return packages;
    }

    private void sendNewOrder(List<Parcel> parcels, int trucksCount, UnloadTrucksUserCommandDto command) {
        var placedCellsCount = parcels.stream()
                .mapToInt(parcel -> parcel.getSize())
                .sum();
        var order = new OrderDto(command.userId(), new Date(), Command.UNLOAD_TRUCKS, trucksCount, parcels.size(), placedCellsCount);
        kafkaSenderService.send(topicsConfiguration.billing().orders(), order);
    }
}
