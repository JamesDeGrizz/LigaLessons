package ru.hofftech.liga.lessons.packageloader.service.factory;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.service.ExitUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.HelpUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.PackageService;
import ru.hofftech.liga.lessons.packageloader.service.ProceedUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.RetryUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.UserHelpService;

@AllArgsConstructor
public class UserCommandServiceFactory {
    private final PackageService packageService;
    private final UserHelpService userHelpService;
    private final UserConsoleService userConsoleService;

    public UserCommandService getUserCommandService(Command command) {
        return switch (command) {
            case Exit -> new ExitUserCommandService();
            case Retry -> new RetryUserCommandService();
            case Help -> new HelpUserCommandService(userHelpService);
            case Proceed -> new ProceedUserCommandService(packageService, userConsoleService);
        };
    }
}
