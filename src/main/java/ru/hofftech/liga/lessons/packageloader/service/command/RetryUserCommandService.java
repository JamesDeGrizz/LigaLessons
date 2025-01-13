package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Map;

@Slf4j
public class RetryUserCommandService implements UserCommandService {
    @Override
    public String execute(Map<String, String> arguments) {
        return "Неправильная команда. Попробуйте ещё раз.";
    }
}
