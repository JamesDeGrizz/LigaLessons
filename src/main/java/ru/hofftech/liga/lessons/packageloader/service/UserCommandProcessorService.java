package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

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
            case CreateParcelUserCommandDto create -> userCommandServices.get("createParcelUserCommandService");
            case FindParcelUserCommandDto find -> userCommandServices.get("findParcelUserCommandService");
            case EditParcelUserCommandDto edit -> userCommandServices.get("editParcelUserCommandService");
            case DeleteParcelUserCommandDto delete -> userCommandServices.get("deleteParcelUserCommandService");
            case LoadParcelsUserCommandDto load -> userCommandServices.get("loadParcelsUserCommandService");
            case UnloadTrucksUserCommandDto unload -> userCommandServices.get("unloadTrucksUserCommandService");
            case FindUserOrdersUserCommandDto orders -> userCommandServices.get("findOrdersUserCommandService");
            default -> null;
        };
        if (service == null) {
            return "Введена нераспознанная команда. Попробуйте ещё раз";
        }

        return service.execute(command);
    }
}
