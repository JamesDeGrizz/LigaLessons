package ru.hofftech.liga.lessons.packageloader.service.command;

import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

public class ExitUserCommandService implements UserCommandService {
    @Override
    public void execute() {
        System.exit(0);
    }
}
