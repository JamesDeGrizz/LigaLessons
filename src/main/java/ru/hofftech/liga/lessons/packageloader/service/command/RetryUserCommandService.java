package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

@Slf4j
public class RetryUserCommandService implements UserCommandService {
    @Override
    public void execute() {
        log.error("Неправильная команда. Попробуйте ещё раз.");
    }
}
