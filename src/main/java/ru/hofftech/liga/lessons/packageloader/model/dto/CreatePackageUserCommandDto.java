package ru.hofftech.liga.lessons.packageloader.model.dto;

import java.util.Map;

public record CreatePackageUserCommandDto(String packageId, String form, String symbol) implements BaseUserCommandDto {
    private static final String ARGUMENT_NAME = "-name";
    private static final String ARGUMENT_SYMBOL = "-symbol";
    private static final String ARGUMENT_FORM = "-form";

    public static CreatePackageUserCommandDto fromArgsMap(Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return null;
        }

        String name = null;
        String form = null;
        String symbol = null;

        if (args.containsKey(ARGUMENT_NAME)) {
            name = args.get(ARGUMENT_NAME);
        }
        if (args.containsKey(ARGUMENT_SYMBOL)) {
            symbol = args.get(ARGUMENT_SYMBOL);
        }
        if (args.containsKey(ARGUMENT_FORM)) {
            form = args.get(ARGUMENT_FORM);
        }

        return new CreatePackageUserCommandDto(name, form, symbol);
    }
}
