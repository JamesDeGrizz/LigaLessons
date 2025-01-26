package ru.hofftech.liga.lessons.consoleclient.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.consoleclient.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.UnloadTrucksUserCommandDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * todo удалить после перехода на rest
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
    private static final String ORDERS_COMMAND = "orders";

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
    public BaseUserCommandDto parse(String userCommand) {
        var commandMatcher = commandPattern.matcher(userCommand);

        if (!commandMatcher.find()) {
            throw new IllegalArgumentException("Неправильная команда: " + userCommand);
        }

        var args = parseArguments(userCommand);
        return switch (commandMatcher.group(COMMAND_GROUP_NUMBER)) {
            case CREATE_COMMAND -> CreateParcelUserCommandDto.fromArgsMap(args);
            case FIND_COMMAND -> FindParcelUserCommandDto.fromArgsMap(args);
            case EDIT_COMMAND -> EditParcelUserCommandDto.fromArgsMap(args);
            case DELETE_COMMAND -> DeleteParcelUserCommandDto.fromArgsMap(args);
            case LOAD_COMMAND -> LoadParcelsUserCommandDto.fromArgsMap(args);
            case UNLOAD_COMMAND -> UnloadTrucksUserCommandDto.fromArgsMap(args);
            case ORDERS_COMMAND -> FindUserOrdersUserCommandDto.fromArgsMap(args);
            default -> null;
        };
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
