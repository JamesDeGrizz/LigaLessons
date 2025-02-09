package ru.hofftech.liga.lessons.telegramclient.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.telegramclient.model.ParsedCommand;
import ru.hofftech.liga.lessons.telegramclient.model.dto.LoadParcelsRequestDto;
import ru.hofftech.liga.lessons.telegramclient.model.dto.UnloadTrucksRequestDto;
import ru.hofftech.liga.lessons.telegramclient.model.enums.Command;

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

    private static final String ARGUMENT_NAME = "--name";
    private static final String ARGUMENT_SYMBOL = "--symbol";
    private static final String ARGUMENT_FORM = "--form";
    private static final String ARGUMENT_TARGET_PARCEL_NAME = "--targetParcelName";
    private static final String ARGUMENT_USER_ID = "--user-id";

    private static final String ARGUMENT_OUT_TYPE = "--out";
    private static final String ARGUMENT_OUT_FILENAME = "--out-filename";
    private static final String ARGUMENT_PARCELS_TEXT = "--parcels-text";
    private static final String ARGUMENT_PARCELS_FILE = "--parcels-file";
    private static final String ARGUMENT_ALGORITHM_TYPE = "--type";
    private static final String ARGUMENT_TRUCKS = "--trucks";

    private static final String ARGUMENT_IN_FILE = "--infile";
    private static final String ARGUMENT_OUT_FILE = "--outfile";
    private static final String ARGUMENT_WITH_COUNT = "--withcount";

    public ParsedCommand parse(String userCommand) {
        var commandMatcher = commandPattern.matcher(userCommand);

        if (!commandMatcher.find()) {
            throw new IllegalArgumentException("Неправильная команда: " + userCommand);
        }

        var args = parseArguments(userCommand);
        return switch (commandMatcher.group(COMMAND_GROUP_NUMBER)) {
            case CREATE_COMMAND -> new ParsedCommand(Command.CREATE_PARCEL, args);
            case FIND_COMMAND -> new ParsedCommand(Command.FIND_PARCEL, args);
            case EDIT_COMMAND -> new ParsedCommand(Command.EDIT_PARCEL, args);
            case DELETE_COMMAND -> new ParsedCommand(Command.DELETE_PARCEL, args);
            case LOAD_COMMAND -> new ParsedCommand(Command.LOAD_PARCELS, args);
            case UNLOAD_COMMAND -> new ParsedCommand(Command.UNLOAD_TRUCKS, args);
            case ORDERS_COMMAND -> new ParsedCommand(Command.SHOW_ORDERS, args);
            default -> null;
        };
    }

    public String getName(Map<String, String> args) {
        if (args == null || args.isEmpty() || !args.containsKey(ARGUMENT_NAME)) {
            return null;
        }

        return args.get(ARGUMENT_NAME);
    }

    public String getSymbol(Map<String, String> args) {
        if (args == null || args.isEmpty() || !args.containsKey(ARGUMENT_SYMBOL)) {
            return null;
        }

        return args.get(ARGUMENT_SYMBOL);
    }

    public String getForm(Map<String, String> args) {
        if (args == null || args.isEmpty() || !args.containsKey(ARGUMENT_FORM)) {
            return null;
        }

        return args.get(ARGUMENT_FORM);
    }

    public String getTargetParcelName(Map<String, String> args) {
        if (args == null || args.isEmpty() || !args.containsKey(ARGUMENT_TARGET_PARCEL_NAME)) {
            return null;
        }

        return args.get(ARGUMENT_TARGET_PARCEL_NAME);
    }

    public String getUserId(Map<String, String> args) {
        if (args == null || args.isEmpty() || !args.containsKey(ARGUMENT_USER_ID)) {
            return null;
        }

        return args.get(ARGUMENT_USER_ID);
    }

    public LoadParcelsRequestDto getLoadParcelsRequestDto(Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return null;
        }

        String out = null;
        String type = null;
        String trucks = null;
        String userId = null;
        String outFilename = null;
        String parcelsText = null;
        String parcelsFile = null;

        if (args.containsKey(ARGUMENT_OUT_TYPE)) {
            out = args.get(ARGUMENT_OUT_TYPE);
        }
        if (args.containsKey(ARGUMENT_ALGORITHM_TYPE)) {
            type = args.get(ARGUMENT_ALGORITHM_TYPE);
        }
        if (args.containsKey(ARGUMENT_TRUCKS)) {
            trucks = args.get(ARGUMENT_TRUCKS);
        }
        if (args.containsKey(ARGUMENT_USER_ID)) {
            userId = args.get(ARGUMENT_USER_ID);
        }
        if (args.containsKey(ARGUMENT_OUT_FILENAME)) {
            outFilename = args.get(ARGUMENT_OUT_FILENAME);
        }
        if (args.containsKey(ARGUMENT_PARCELS_TEXT)) {
            parcelsText = args.get(ARGUMENT_PARCELS_TEXT);
        }
        if (args.containsKey(ARGUMENT_PARCELS_FILE)) {
            parcelsFile = args.get(ARGUMENT_PARCELS_FILE);
        }

        return new LoadParcelsRequestDto(out, type, trucks, userId, outFilename, parcelsText, parcelsFile);
    }

    public UnloadTrucksRequestDto getUnloadTrucksRequestDto(Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return null;
        }

        String infile = null;
        String outfile = null;
        String userId = null;
        Boolean withCount = null;

        if (args.containsKey(ARGUMENT_IN_FILE)) {
            infile = args.get(ARGUMENT_IN_FILE);
        }
        if (args.containsKey(ARGUMENT_OUT_FILE)) {
            outfile = args.get(ARGUMENT_OUT_FILE);
        }
        withCount = args.containsKey(ARGUMENT_WITH_COUNT);
        if (args.containsKey(ARGUMENT_USER_ID)) {
            userId = args.get(ARGUMENT_USER_ID);
        }

        return new UnloadTrucksRequestDto(infile, outfile, userId, withCount);
    }

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
