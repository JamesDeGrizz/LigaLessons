package ru.hofftech.liga.lessons.telegramclient.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.telegramclient.model.dto.UnloadTrucksUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class UnloadTrucksUserCommandValidator {
    public List<String> validate(UnloadTrucksUserCommandDto command) {
        List<String> errors = new ArrayList<>();
        validateSourceFileName(command, errors);
        validateReportFileName(command, errors);
        validateUserId(command, errors);
        return errors;
    }

    private void validateUserId(UnloadTrucksUserCommandDto command, List<String> errors) {
        if (command.userId() == null || command.userId().isEmpty()) {
            errors.add("Не хватает аргумента \"user-id\"");
        }
    }

    private void validateReportFileName(UnloadTrucksUserCommandDto command, List<String> errors) {
        if (command.outfile() == null || command.outfile().isEmpty()) {
            errors.add("Не хватает аргумента \"outfile\"");
        }
    }

    private void validateSourceFileName(UnloadTrucksUserCommandDto command, List<String> errors) {
        if (command.infile() == null || command.infile().isEmpty()) {
            errors.add("Не хватает аргумента \"infile\"");
        }
    }
}
