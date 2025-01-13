package ru.hofftech.liga.lessons.packageloader.service.command;

import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Map;

/**
 * Сервис для повторного выполнения команды пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет метод для обработки команды повторного выполнения.
 */
public class RetryUserCommandService implements UserCommandService {
    /**
     * Выполняет команду повторного выполнения.
     *
     * @param arguments аргументы команды (не используются в данном методе)
     * @return сообщение о неправильной команде и предложении попробовать ещё раз
     */
    @Override
    public String execute(Map<String, String> arguments) {
        return "Неправильная команда. Попробуйте ещё раз.";
    }
}
