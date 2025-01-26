package ru.hofftech.liga.lessons.telegramclient.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.telegramclient.model.dto.DeleteParcelUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class DeleteParcelUserCommandValidator {

    public List<String> validate(DeleteParcelUserCommandDto command) {
        List<String> errors = new ArrayList<>();
        validateParcelName(command, errors);
        return errors;
    }

    private void validateParcelName(DeleteParcelUserCommandDto command, List<String> errors) {
        if (command.parcelId() == null || command.parcelId().isEmpty()) {
            errors.add("Не хватает аргумента \"name\"");
        }
    }
}
