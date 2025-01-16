package ru.hofftech.liga.lessons.packageloader.validator;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class UnloadTrucksUserCommandValidator {
    private static final String ARGUMENT_IN_FILE = "-infile";
    private static final String ARGUMENT_OUT_FILE = "-outfile";

    public List<String> validate(Map<String, String> arguments) {
        List<String> errors = new ArrayList<String>();
        validateSourceFileName(arguments, errors);
        validateReportFileName(arguments, errors);
        return errors;
    }

    private void validateReportFileName(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_OUT_FILE)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_OUT_FILE + "\"");
        }
    }

    private void validateSourceFileName(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_IN_FILE)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_IN_FILE + "\"");
        }
    }
}
