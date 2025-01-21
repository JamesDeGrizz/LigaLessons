package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.service.command.CreateParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.DeleteParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.EditParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindUserOrdersCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadParcelsUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.UnloadTrucksUserCommandService;

@AllArgsConstructor
public class UserCommandProcessorService {
    private final UserCommandParserService userCommandParserService;
    private final ApplicationContext applicationContext;

    public String processRawInput(String userInput) {
        var dto = userCommandParserService.parse(userInput);
        if (dto == null) {
            return "Введена нераспознанная команда. Попробуйте ещё раз";
        }

        return processCommand(dto);
    }

    public String processCommand(BaseUserCommandDto command) {
        var service = switch (command) {
            case CreateParcelUserCommandDto create -> applicationContext.getBean(CreateParcelUserCommandService.class);
            case FindParcelUserCommandDto find -> applicationContext.getBean(FindParcelUserCommandService.class);
            case EditParcelUserCommandDto edit -> applicationContext.getBean(EditParcelUserCommandService.class);
            case DeleteParcelUserCommandDto delete -> applicationContext.getBean(DeleteParcelUserCommandService.class);
            case LoadParcelsUserCommandDto load -> applicationContext.getBean(LoadParcelsUserCommandService.class);
            case UnloadTrucksUserCommandDto unload -> applicationContext.getBean(UnloadTrucksUserCommandService.class);
            case FindUserOrdersUserCommandDto orders -> applicationContext.getBean(FindUserOrdersCommandService.class);
            default -> null;
        };
        if (service == null) {
            return "Введена нераспознанная команда. Попробуйте ещё раз";
        }

        return service.execute(command);
    }
}
