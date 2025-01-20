package ru.hofftech.liga.lessons.packageloader.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditPackageUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class EditPackageUserCommandValidator {

    public List<String> validate(EditPackageUserCommandDto command) {
        List<String> errors = new ArrayList<>();
        validatePackageId(command, errors);
        validatePackageName(command, errors);
        validatePackageSymbol(command, errors);
        validatePackageContent(command, errors);
        return errors;
    }

    private void validatePackageId(EditPackageUserCommandDto command, List<String> errors) {
        if (command.currentPackageId() == null || command.currentPackageId().isEmpty()) {
            errors.add("Не хватает аргумента \"id\"");
        }
    }

    private void validatePackageName(EditPackageUserCommandDto command, List<String> errors) {
        if (command.newPackageId() == null || command.newPackageId().isEmpty()) {
            errors.add("Не хватает аргумента \"name\"");
        }
    }

    private void validatePackageSymbol(EditPackageUserCommandDto command, List<String> errors) {
        if (command.symbol() == null || command.symbol().isEmpty()) {
            errors.add("Не хватает аргумента \"symbol\"");
        }
    }

    private void validatePackageContent(EditPackageUserCommandDto command, List<String> errors) {
        if (command.form() == null || command.form().isEmpty()) {
            errors.add("Не хватает аргумента \"form\"");
        }
    }
}
