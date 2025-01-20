package ru.hofftech.liga.lessons.packageloader.model.dto;

import java.util.Map;

public record EditPackageUserCommandDto(String currentPackageId, String newPackageId, String form, String symbol) implements BaseUserCommandDto {
    private static final String ARGUMENT_ID = "-id";
    private static final String ARGUMENT_NAME = "-name";
    private static final String ARGUMENT_SYMBOL = "-symbol";
    private static final String ARGUMENT_FORM = "-form";

    public static EditPackageUserCommandDto fromArgsMap(Map<String, String> args) {
        if (args == null || args.isEmpty()) {
            return null;
        }

        String id = null;
        String name = null;
        String form = null;
        String symbol = null;

        if (args.containsKey(ARGUMENT_ID)) {
            id = args.get(ARGUMENT_ID);
        }
        if (args.containsKey(ARGUMENT_NAME)) {
            name = args.get(ARGUMENT_NAME);
        }
        if (args.containsKey(ARGUMENT_SYMBOL)) {
            symbol = args.get(ARGUMENT_SYMBOL);
        }
        if (args.containsKey(ARGUMENT_FORM)) {
            form = args.get(ARGUMENT_FORM);
        }

        return new EditPackageUserCommandDto(id, name, form, symbol);
    }
}
