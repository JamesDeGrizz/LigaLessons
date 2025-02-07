package ru.hofftech.liga.lessons.packageloader.service;

import lombok.RequiredArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksResponseDto;
import ru.hofftech.liga.lessons.packageloader.model.entity.OrderOutbox;
import ru.hofftech.liga.lessons.packageloader.model.entity.OrderOutboxId;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.packageloader.validator.UnloadTrucksUserCommandValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Сервис для разгрузки грузовиков на основе команд пользователя.
 */
@RequiredArgsConstructor
public class UnloadTrucksService {
    private final FileLoaderService fileLoaderService;
    private final ReportParcelService reportParcelService;
    private final UnloadTrucksUserCommandValidator commandValidator;
    private final OrderRepository orderRepository;

    public UnloadTrucksResponseDto execute(UnloadTrucksUserCommandDto command) {
        if (command == null) {
            throw new IllegalArgumentException("Посылки не могут быть разгружены: \nПередан пустой список аргументов");
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException("Посылки не могут быть разгружены: \n" + String.join("\n", validationErrors));
        }

        var trucks = fileLoaderService.getTrucks(command.infile());
        if (trucks.isEmpty()) {
            throw new IllegalArgumentException("Не удалось загрузить грузовики из файла");
        }

        var parcels = getParcelsFromTrucks(trucks);

        reportParcelService.reportParcels(parcels, command.withCount());
        reportParcelService.saveParcelsToFile(command.outfile(), parcels, command.withCount());

        orderRepository.save(
                OrderOutbox.builder()
                        .id(OrderOutboxId.builder()
                                .name(command.userId())
                                .date(new Date())
                                .operation(Command.UNLOAD_TRUCKS.toString())
                                .build())
                        .trucksCount(trucks.size())
                        .parcelsCount(parcels.size())
                        .cellsCount(parcels.stream()
                                .mapToInt(Parcel::getSize)
                                .sum())
                        .build());

        return new UnloadTrucksResponseDto("Посылки успешно разгружены");
    }

    private List<Parcel> getParcelsFromTrucks(List<Truck> trucks) {
        var packages = new ArrayList<Parcel>();
        trucks.forEach(truck -> {
            packages.addAll(truck.getParcels());
        });
        return packages;
    }
}
