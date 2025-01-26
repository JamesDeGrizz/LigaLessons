package ru.hofftech.liga.lessons.consoleclient.service;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.consoleclient.config.BeanNameConfig;
import ru.hofftech.liga.lessons.consoleclient.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.service.interfaces.UserCommandService;

import java.util.Map;

@AllArgsConstructor
public class UserCommandProcessorService {
    private final UserCommandParserService userCommandParserService;
    private final Map<String, UserCommandService> userCommandServices;

    public String processRawInput(String userInput) {
        var dto = userCommandParserService.parse(userInput);
        if (dto == null) {
            return "Введена нераспознанная команда. Попробуйте ещё раз";
        }

        return processCommand(dto);
    }

    public String processCommand(BaseUserCommandDto command) {
        var service = switch (command) {
            case CreateParcelUserCommandDto create -> userCommandServices.get(BeanNameConfig.CREATE_PARCEL);
            case FindParcelUserCommandDto find -> userCommandServices.get(BeanNameConfig.FIND_PARCEL);
            case EditParcelUserCommandDto edit -> userCommandServices.get(BeanNameConfig.EDIT_PARCEL);
            case DeleteParcelUserCommandDto delete -> userCommandServices.get(BeanNameConfig.DELETE_PARCEL);
            case LoadParcelsUserCommandDto load -> userCommandServices.get(BeanNameConfig.LOAD_PARCELS);
            case UnloadTrucksUserCommandDto unload -> userCommandServices.get(BeanNameConfig.UNLOAD_TRUCKS);
            case FindUserOrdersUserCommandDto orders -> userCommandServices.get(BeanNameConfig.SHOW_ORDERS);
            default -> null;
        };
        if (service == null) {
            return "Введена нераспознанная команда. Попробуйте ещё раз";
        }

        return service.execute(command);
    }
}
