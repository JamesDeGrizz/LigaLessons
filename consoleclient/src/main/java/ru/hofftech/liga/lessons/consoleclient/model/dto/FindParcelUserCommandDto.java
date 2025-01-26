package ru.hofftech.liga.lessons.consoleclient.model.dto;

import java.util.Map;

public record FindParcelUserCommandDto(String parcelId) implements BaseUserCommandDto {
    private static final String ARGUMENT_NAME = "-name";

    public static FindParcelUserCommandDto fromArgsMap(Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return null;
        }

        String name = null;

        if (args.containsKey(ARGUMENT_NAME)) {
            name = args.get(ARGUMENT_NAME);
        }

        return new FindParcelUserCommandDto(name);
    }
}
