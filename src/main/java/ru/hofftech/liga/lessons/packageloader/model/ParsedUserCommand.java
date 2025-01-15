package ru.hofftech.liga.lessons.packageloader.model;

import ru.hofftech.liga.lessons.packageloader.model.enums.Command;

import java.util.Map;

/**
 * Запись, представляющая разобранную команду пользователя.
 * Этот класс содержит команду и аргументы, которые были извлечены из строки команды пользователя.
 *
 * @param command команда, которую нужно выполнить
 * @param arguments аргументы команды в виде карты, где ключ - имя аргумента, а значение - значение аргумента
 */
public record ParsedUserCommand(Command command, Map<String, String> arguments) {
}
