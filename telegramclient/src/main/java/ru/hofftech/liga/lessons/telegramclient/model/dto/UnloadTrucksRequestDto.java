package ru.hofftech.liga.lessons.telegramclient.model.dto;

import java.util.Map;

public record UnloadTrucksRequestDto(String infile,
                                     String outfile,
                                     String userId,
                                     boolean withCount) {
    private static final String ARGUMENT_IN_FILE = "--infile";
    private static final String ARGUMENT_OUT_FILE = "--outfile";
    private static final String ARGUMENT_WITH_COUNT = "--withcount";
    private static final String ARGUMENT_USER_ID = "--user-id";

    public static UnloadTrucksRequestDto fromArgsMap(Map<String, String> args) {
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
}
