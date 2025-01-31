package ru.hofftech.liga.lessons.telegramclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.hofftech.liga.lessons.telegramclient.model.enums.Command;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ParsedCommand {
    private Command command;
    private Map<String, String> args;
}
