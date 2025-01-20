package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreatePackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeletePackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditPackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindPackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadPackagesUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.service.command.CreatePackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.DeletePackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.EditPackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindPackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindUserOrdersCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadPackagesUserCommandService;
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
        var qwe = command instanceof CreatePackageUserCommandDto;

        var service = switch (command) {
            case CreatePackageUserCommandDto create -> applicationContext.getBean(CreatePackageUserCommandService.class);
            case FindPackageUserCommandDto find -> applicationContext.getBean(FindPackageUserCommandService.class);
            case EditPackageUserCommandDto edit -> applicationContext.getBean(EditPackageUserCommandService.class);
            case DeletePackageUserCommandDto delete -> applicationContext.getBean(DeletePackageUserCommandService.class);
            case LoadPackagesUserCommandDto load -> applicationContext.getBean(LoadPackagesUserCommandService.class);
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
