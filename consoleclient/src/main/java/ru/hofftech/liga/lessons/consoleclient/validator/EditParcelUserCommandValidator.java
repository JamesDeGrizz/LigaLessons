package ru.hofftech.liga.lessons.consoleclient.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.consoleclient.model.dto.EditParcelUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class EditParcelUserCommandValidator {

    public List<String> validate(EditParcelUserCommandDto command) {
        List<String> errors = new ArrayList<>();
        validateParcelId(command, errors);
        validateParcelName(command, errors);
        validatePackageSymbol(command, errors);
        validateParcelContent(command, errors);
        return errors;
    }

    private void validateParcelId(EditParcelUserCommandDto command, List<String> errors) {
        if (command.currentParcelId() == null || command.currentParcelId().isEmpty()) {
            errors.add("Не хватает аргумента \"id\"");
        }
    }

    private void validateParcelName(EditParcelUserCommandDto command, List<String> errors) {
        if (command.newParcelId() == null || command.newParcelId().isEmpty()) {
            errors.add("Не хватает аргумента \"name\"");
        }
    }

    private void validatePackageSymbol(EditParcelUserCommandDto command, List<String> errors) {
        if (command.symbol() == null || command.symbol().isEmpty()) {
            errors.add("Не хватает аргумента \"symbol\"");
        }
    }

    private void validateParcelContent(EditParcelUserCommandDto command, List<String> errors) {
        if (command.form() == null || command.form().isEmpty()) {
            errors.add("Не хватает аргумента \"form\"");
        }
    }
}
