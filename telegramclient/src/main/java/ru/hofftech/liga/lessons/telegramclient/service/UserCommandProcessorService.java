package ru.hofftech.liga.lessons.telegramclient.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.telegramclient.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.telegramclient.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.telegramclient.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.telegramclient.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.telegramclient.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.telegramclient.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.telegramclient.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.telegramclient.model.enums.Command;

@AllArgsConstructor
@Service
public class UserCommandProcessorService {
    private final UserCommandParserService userCommandParserService;
    private final ParcelLoaderClient parcelLoaderClientService;

    public String processRawInput(String userInput) {
        try {
            var command = userCommandParserService.parse(userInput);
            if (command == null) {
                return "Введена нераспознанная команда. Попробуйте ещё раз";
            }

            return switch (command.getCommand()) {
                case Command.CREATE_PARCEL -> parcelLoaderClientService.createParcel(CreateParcelUserCommandDto.fromArgsMap(command.getArgs()));
                case Command.FIND_PARCEL -> parcelLoaderClientService.getParcel(FindParcelUserCommandDto.fromArgsMap(command.getArgs()).parcelId());
                case Command.EDIT_PARCEL -> parcelLoaderClientService.updateParcel(EditParcelUserCommandDto.fromArgsMap(command.getArgs()).currentParcelId(), CreateParcelUserCommandDto.fromArgsMap(command.getArgs()));
                case Command.DELETE_PARCEL -> parcelLoaderClientService.deleteParcel(DeleteParcelUserCommandDto.fromArgsMap(command.getArgs()).parcelId());
                case Command.LOAD_PARCELS -> parcelLoaderClientService.load(LoadParcelsUserCommandDto.fromArgsMap(command.getArgs()));
                case Command.UNLOAD_TRUCKS -> parcelLoaderClientService.unload(UnloadTrucksUserCommandDto.fromArgsMap(command.getArgs()));
                case Command.SHOW_ORDERS -> parcelLoaderClientService.findUserOrders(FindUserOrdersUserCommandDto.fromArgsMap(command.getArgs()).userId());
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
