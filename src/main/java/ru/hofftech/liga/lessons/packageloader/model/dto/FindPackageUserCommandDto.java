package ru.hofftech.liga.lessons.packageloader.model.dto;

import java.util.Map;

public record FindPackageUserCommandDto(String packageId) implements BaseUserCommandDto {
    private static final String ARGUMENT_NAME = "-name";

    public static FindPackageUserCommandDto fromArgsMap(Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return null;
        }

        String name = null;

        if (args.containsKey(ARGUMENT_NAME)) {
            name = args.get(ARGUMENT_NAME);
        }

        return new FindPackageUserCommandDto(name);
    }
}
