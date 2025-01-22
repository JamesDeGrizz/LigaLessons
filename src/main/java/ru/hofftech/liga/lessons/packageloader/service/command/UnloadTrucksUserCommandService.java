package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.service.BillingService;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportParcelService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.UnloadTrucksUserCommandValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для разгрузки грузовиков на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд разгрузки грузовиков.
 */
@AllArgsConstructor
public class UnloadTrucksUserCommandService implements UserCommandService {
    private final FileLoaderService fileLoaderService;
    private final ReportParcelService reportParcelService;
    private final UnloadTrucksUserCommandValidator commandValidator;
    private final BillingService billingService;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылки не могут быть разгружены: \nПередан пустой список аргументов";
        }
        if (!(command instanceof UnloadTrucksUserCommandDto)) {
            return "Посылки не могут быть разгружены: \nПередана команда неправильного типа";
        }

        var castedCommand = (UnloadTrucksUserCommandDto) command;

        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылки не могут быть разгружены: \n" + String.join("\n", validationErrors);
        }

        var trucks = fileLoaderService.getTrucks(castedCommand.infile());
        if (trucks.isEmpty()) {
            return "Не удалось загрузить грузовики из файла";
        }

        var packages = getPackagesFromTrucks(trucks);

        reportParcelService.reportParcels(packages, castedCommand.withCount());
        reportParcelService.saveParcelsToFile(castedCommand.outfile(), packages, castedCommand.withCount());

        billingService.saveOrder(castedCommand.userId(), Command.UNLOAD_TRUCKS, trucks.size(), packages);

        return "Посылки успешно разгружены";
    }

    private List<Parcel> getPackagesFromTrucks(List<Truck> trucks) {
        var packages = new ArrayList<Parcel>();
        trucks.forEach(truck -> {
            packages.addAll(truck.getParcels());
        });
        return packages;
    }
}
