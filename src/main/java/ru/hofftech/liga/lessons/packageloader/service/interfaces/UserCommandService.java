package ru.hofftech.liga.lessons.packageloader.service.interfaces;

import java.util.Map;

/**
 * Интерфейс для сервисов команд пользователя.
 * Реализации этого интерфейса должны предоставлять методы для выполнения команд, связанных с управлением посылками.
 */
public interface UserCommandService {
    /**
     * Выполняет команду на основе переданных аргументов.
     *
     * @param arguments аргументы команды
     * @return результат выполнения команды в виде строки
     */
    String execute(Map<String, String> arguments);
}
