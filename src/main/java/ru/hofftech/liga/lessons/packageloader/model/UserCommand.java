package ru.hofftech.liga.lessons.packageloader.model;

import ru.hofftech.liga.lessons.packageloader.model.enums.CommandSource;

/**
 * Представляет команду пользователя, включающую источник команды и саму команду.
 * Этот класс используется для обработки команд, связанных с управлением посылками.
 *
 * @param commandSource источник команды
 * @param command команда
 */
public record UserCommand(CommandSource commandSource, String command) {
}
