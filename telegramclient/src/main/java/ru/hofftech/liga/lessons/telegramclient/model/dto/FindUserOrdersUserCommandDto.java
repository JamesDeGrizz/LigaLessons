package ru.hofftech.liga.lessons.telegramclient.model.dto;

import java.util.Map;

public record FindUserOrdersUserCommandDto(String userId) implements BaseUserCommandDto {
    private static final String ARGUMENT_ID = "-user-id";

    public static FindUserOrdersUserCommandDto fromArgsMap(Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return null;
        }

        String id = null;

        if (args.containsKey(ARGUMENT_ID)) {
            id = args.get(ARGUMENT_ID);
        }

        return new FindUserOrdersUserCommandDto(id);
    }
}
