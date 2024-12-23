package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.service.UserHelpService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

@AllArgsConstructor
public class HelpUserCommandService implements UserCommandService {
    private final UserHelpService userHelpService;

    @Override
    public void execute() {
        userHelpService.printHelp();
    }
}
