package ru.hofftech.liga.lessons.packageloader.validator;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class CreatePackageUserCommandValidator {
    private static final String ARGUMENT_NAME = "-name";
    private static final String ARGUMENT_SYMBOL = "-symbol";
    private static final String ARGUMENT_FORM = "-form";

    public List<String> validate(Map<String, String> arguments) {
        List<String> errors = new ArrayList<String>();
        validatePackageName(arguments, errors);
        validatePackageSymbol(arguments, errors);
        validatePackageContent(arguments, errors);
        return errors;
    }

    private void validatePackageName(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_NAME)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_NAME + "\"");
        }
    }

    private void validatePackageSymbol(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_SYMBOL)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_SYMBOL + "\"");
        }
    }

    private void validatePackageContent(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_FORM)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_FORM + "\"");
        }
    }
}
