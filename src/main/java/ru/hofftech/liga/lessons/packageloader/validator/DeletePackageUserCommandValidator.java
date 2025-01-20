package ru.hofftech.liga.lessons.packageloader.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeletePackageUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class DeletePackageUserCommandValidator {

    public List<String> validate(DeletePackageUserCommandDto command) {
        List<String> errors = new ArrayList<>();
        validatePackageName(command, errors);
        return errors;
    }

    private void validatePackageName(DeletePackageUserCommandDto command, List<String> errors) {
        if (command.packageId() == null || command.packageId().isEmpty()) {
            errors.add("Не хватает аргумента \"name\"");
        }
    }
}
