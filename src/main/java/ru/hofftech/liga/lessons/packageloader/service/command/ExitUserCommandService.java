package ru.hofftech.liga.lessons.packageloader.service.command;

import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Map;

public class ExitUserCommandService implements UserCommandService {
    @Override
    public String execute(Map<String, String> arguments) {
        System.exit(0);
        return null;
    }
}
