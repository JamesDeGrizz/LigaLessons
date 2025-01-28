package ru.hofftech.liga.lessons.telegramclient.model.dto;

import java.util.Map;

public record DeleteParcelUserCommandDto(String parcelId) {
    private static final String ARGUMENT_NAME = "--name";

    public static DeleteParcelUserCommandDto fromArgsMap(Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return null;
        }

        String name = null;

        if (args.containsKey(ARGUMENT_NAME)) {
            name = args.get(ARGUMENT_NAME);
        }

        return new DeleteParcelUserCommandDto(name);
    }
}
