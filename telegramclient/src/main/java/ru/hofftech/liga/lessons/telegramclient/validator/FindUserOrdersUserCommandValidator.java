package ru.hofftech.liga.lessons.telegramclient.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.telegramclient.model.dto.FindUserOrdersUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class FindUserOrdersUserCommandValidator {
    public List<String> validate(FindUserOrdersUserCommandDto command) {
        List<String> errors = new ArrayList<>();
        validateUserId(command, errors);
        return errors;
    }

    private void validateUserId(FindUserOrdersUserCommandDto command, List<String> errors) {
        if (command.userId() == null || command.userId().isEmpty()) {
            errors.add("Не хватает аргумента \"user-id\"");
        }
    }
}
