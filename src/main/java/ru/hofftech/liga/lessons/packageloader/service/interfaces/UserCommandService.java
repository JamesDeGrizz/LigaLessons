package ru.hofftech.liga.lessons.packageloader.service.interfaces;

import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;

/**
 * Интерфейс для сервисов команд пользователя.
 * Реализации этого интерфейса должны предоставлять методы для выполнения команд, связанных с управлением посылками.
 */
public interface UserCommandService {
    String execute(BaseUserCommandDto command);
}
