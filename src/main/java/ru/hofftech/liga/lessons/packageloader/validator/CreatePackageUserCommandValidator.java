package ru.hofftech.liga.lessons.packageloader.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreatePackageUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CreatePackageUserCommandValidator {

    public List<String> validate(CreatePackageUserCommandDto command) {
        List<String> errors = new ArrayList<String>();
        validatePackageName(command, errors);
        validatePackageSymbol(command, errors);
        validatePackageContent(command, errors);
        return errors;
    }

    private void validatePackageName(CreatePackageUserCommandDto command, List<String> errors) {
        if (command.packageId() == null || command.packageId().isEmpty()) {
            errors.add("Не хватает аргумента \"name\"");
        }
    }

    private void validatePackageSymbol(CreatePackageUserCommandDto command, List<String> errors) {
        if (command.symbol() == null || command.symbol().isEmpty()) {
            errors.add("Не хватает аргумента \"symbol\"");
        }
    }

    private void validatePackageContent(CreatePackageUserCommandDto command, List<String> errors) {
        if (command.form() == null || command.form().isEmpty()) {
            errors.add("Не хватает аргумента \"form\"");
        }
    }
}
