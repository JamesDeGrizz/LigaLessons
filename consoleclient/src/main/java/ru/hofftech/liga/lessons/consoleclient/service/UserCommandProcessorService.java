package ru.hofftech.liga.lessons.consoleclient.service;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.consoleclient.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.UnloadTrucksUserCommandDto;

@AllArgsConstructor
public class UserCommandProcessorService {
    private final ParcelLoaderClientService parcelLoaderClientService;

    public String processCommand(BaseUserCommandDto command) {
        return switch (command) {
            case CreateParcelUserCommandDto create -> parcelLoaderClientService.createParcel(create);
            case FindParcelUserCommandDto find -> parcelLoaderClientService.findUserOrders(find.parcelId());
            case EditParcelUserCommandDto edit -> parcelLoaderClientService.updateParcel(edit.currentParcelId(), new CreateParcelUserCommandDto(edit.newParcelId(), edit.form(), edit.symbol()));
            case DeleteParcelUserCommandDto delete -> parcelLoaderClientService.deleteParcel(delete.parcelId());
            case LoadParcelsUserCommandDto load -> parcelLoaderClientService.load(load);
            case UnloadTrucksUserCommandDto unload -> parcelLoaderClientService.unload(unload);
            case FindUserOrdersUserCommandDto orders -> parcelLoaderClientService.findUserOrders(orders.userId());
            default -> "Введена нераспознанная команда. Попробуйте ещё раз";
        };
    }
}
