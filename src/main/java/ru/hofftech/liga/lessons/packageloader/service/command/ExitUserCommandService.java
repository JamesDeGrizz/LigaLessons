package ru.hofftech.liga.lessons.packageloader.service.command;

import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Map;

/**
 * Сервис для выполнения команды выхода из приложения.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет метод для завершения работы приложения.
 */
public class ExitUserCommandService implements UserCommandService {
    /**
     * Выполняет команду выхода из приложения.
     *
     * @param arguments аргументы команды (не используются в данном методе)
     * @return null, так как метод завершает работу приложения
     */
    @Override
    public String execute(Map<String, String> arguments) {
        System.exit(0);
        return null;
    }
}
