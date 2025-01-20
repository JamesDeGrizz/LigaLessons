package ru.hofftech.liga.lessons.packageloader.model.dto;

import java.util.Map;

public record LoadPackagesUserCommandDto(String out,
                                         String type,
                                         String trucks,
                                         String userId,
                                         String outFilename,
                                         String parcelsText,
                                         String parcelsFile) implements BaseUserCommandDto {
    private static final String ARGUMENT_OUT_TYPE = "-out";
    private static final String ARGUMENT_OUT_FILENAME = "-out-filename";
    private static final String ARGUMENT_PACKAGES_TEXT = "-parcels-text";
    private static final String ARGUMENT_PACKAGES_FILE = "-parcels-file";
    private static final String ARGUMENT_ALGORITHM_TYPE = "-type";
    private static final String ARGUMENT_TRUCKS = "-trucks";
    private static final String ARGUMENT_USER_ID = "-user-id";

    public static LoadPackagesUserCommandDto fromArgsMap(Map<String, String> args) {
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
        if (args.containsKey(ARGUMENT_PACKAGES_TEXT)) {
            parcelsText = args.get(ARGUMENT_PACKAGES_TEXT);
        }
        if (args.containsKey(ARGUMENT_PACKAGES_FILE)) {
            parcelsFile = args.get(ARGUMENT_PACKAGES_FILE);
        }

        return new LoadPackagesUserCommandDto(out, type, trucks, userId, outFilename, parcelsText, parcelsFile);
    }
}
