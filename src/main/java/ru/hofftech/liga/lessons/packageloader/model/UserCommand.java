package ru.hofftech.liga.lessons.packageloader.model;

import ru.hofftech.liga.lessons.packageloader.model.enums.CommandSource;

public record UserCommand(CommandSource commandSource, String command) {
}
