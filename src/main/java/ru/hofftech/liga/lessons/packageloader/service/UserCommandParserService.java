package ru.hofftech.liga.lessons.packageloader.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.ParsedUserCommand;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Сервис для парсинга команд пользователя и их аргументов.
 * Этот класс предоставляет методы для разбора команд и аргументов, введенных пользователем, и преобразования их в удобный для обработки формат.
 */
@Slf4j
public class UserCommandParserService {
    private static final int COMMAND_GROUP_NUMBER = 1;
    private static final int ARGUMENTS_GROUP_NUMBER = 2;
    private static final int ARGUMENT_NAME_GROUP_NUMBER = 1;
    private static final int ARGUMENT_VALUE_GROUP_NUMBER = 2;
    private static final int ARGUMENT_FLAG_NUMBER = 0;

    private static final String CREATE_COMMAND = "create";
    private static final String FIND_COMMAND = "find";
    private static final String EDIT_COMMAND = "edit";
    private static final String DELETE_COMMAND = "delete";
    private static final String LOAD_COMMAND = "load";
    private static final String UNLOAD_COMMAND = "unload";
    private static final String EXIT_COMMAND = "exit";
    private static final String HELP_COMMAND = "help";

    private static final String COMMAND_REGEX = "(\\w+)(.*)";
    private static final String ARGUMENTS_MAP_REGEX = "(-[^\\s]+)\\s+\"([^\"]+)\"";
    private static final String ONE_ARGUMENT_REGEX = "\"([^\"]+)\"";
    private static final String FLAGS_ARGUMENT_REGEX = "(--[^\\s]+)";
    private final Pattern commandPattern = Pattern.compile(COMMAND_REGEX);
    private final Pattern argumentsPattern = Pattern.compile(ARGUMENTS_MAP_REGEX);
    private final Pattern oneArgumentPattern = Pattern.compile(ONE_ARGUMENT_REGEX);
    private final Pattern flagsArgumentPattern = Pattern.compile(FLAGS_ARGUMENT_REGEX);

    /**
     * Разбирает команду пользователя и возвращает соответствующую команду.
     *
     * @param userCommand команда пользователя
     * @return команда, соответствующая введенной пользователем команде
     */
    public ParsedUserCommand parseCommandAndArguments(String userCommand) {
        var commandMatcher = commandPattern.matcher(userCommand);

        if (!commandMatcher.find()) {
            return new ParsedUserCommand(Command.Retry, null);
        }

        var command = switch (commandMatcher.group(COMMAND_GROUP_NUMBER)) {
            case CREATE_COMMAND -> Command.CreatePackage;
            case FIND_COMMAND -> Command.FindPackage;
            case EDIT_COMMAND -> Command.EditPackage;
            case DELETE_COMMAND -> Command.DeletePackage;
            case LOAD_COMMAND -> Command.LoadPackages;
            case UNLOAD_COMMAND -> Command.UnloadTrucks;
            case EXIT_COMMAND -> Command.Exit;
            case HELP_COMMAND -> Command.Help;
            default -> Command.Retry;
        };

        return new ParsedUserCommand(command, parseArguments(userCommand));
    }

    /**
     * Разбирает аргументы команды пользователя и возвращает их в виде HashMap.
     *
     * @param userCommand команда пользователя
     * @return HashMap аргументов команды
     */
    private Map<String, String> parseArguments(String userCommand) {
        var commandMatcher = commandPattern.matcher(userCommand);

        if (!commandMatcher.find()) {
            return Collections.emptyMap();
        }

        var arguments = commandMatcher.group(ARGUMENTS_GROUP_NUMBER).trim();
        var argumentsMatcher = argumentsPattern.matcher(arguments);

        var argumentsMap = new HashMap<String, String>();
        while (argumentsMatcher.find()) {
            argumentsMap.put(argumentsMatcher.group(ARGUMENT_NAME_GROUP_NUMBER), argumentsMatcher.group(ARGUMENT_VALUE_GROUP_NUMBER));
        }

        if (argumentsMap.isEmpty()) {
            argumentsMatcher = oneArgumentPattern.matcher(arguments);
            while (argumentsMatcher.find()) {
                argumentsMap.put(argumentsMatcher.group(ARGUMENT_NAME_GROUP_NUMBER), null);
            }
        }

        var flagsMatcher = flagsArgumentPattern.matcher(userCommand);
        while (flagsMatcher.find()) {
            argumentsMap.put(flagsMatcher.group(ARGUMENT_FLAG_NUMBER), "");
        }

        return argumentsMap;
    }
}
