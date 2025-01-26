package ru.hofftech.liga.lessons.telegramclient.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.telegramclient.model.dto.CreateParcelUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CreateParcelUserCommandValidator {

    public List<String> validate(CreateParcelUserCommandDto command) {
        List<String> errors = new ArrayList<String>();
        validateParcelName(command, errors);
        validatePackageSymbol(command, errors);
        validateParcelContent(command, errors);
        return errors;
    }

    private void validateParcelName(CreateParcelUserCommandDto command, List<String> errors) {
        if (command.parcelId() == null || command.parcelId().isEmpty()) {
            errors.add("Не хватает аргумента \"name\"");
        }
    }

    private void validatePackageSymbol(CreateParcelUserCommandDto command, List<String> errors) {
        if (command.symbol() == null || command.symbol().isEmpty()) {
            errors.add("Не хватает аргумента \"symbol\"");
        }
    }

    private void validateParcelContent(CreateParcelUserCommandDto command, List<String> errors) {
        if (command.form() == null || command.form().isEmpty()) {
            errors.add("Не хватает аргумента \"form\"");
        }
    }
}
