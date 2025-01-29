package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.validator.DeleteParcelUserCommandValidator;

/**
 * Сервис для удаления посылок на основе команд пользователя.
 */
@AllArgsConstructor
public class DeleteParcelUserCommandService {
    private static final String ERROR_MESSAGE_TEXT = "Посылка не может быть удалена: ";

    private final ParcelRepository parcelRepository;
    private final DeleteParcelUserCommandValidator commandValidator;

    public String execute(DeleteParcelUserCommandDto command) {
        if (command == null) {
            return ERROR_MESSAGE_TEXT + "\nПередан пустой список аргументов";
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            return ERROR_MESSAGE_TEXT + "\n" + String.join("\n", validationErrors);
        }

        var existingParcel = parcelRepository.findByName(command.parcelId());
        if (existingParcel.isEmpty()) {
            return ERROR_MESSAGE_TEXT + "\nпосылка с названием \"{}\" не существует" + command.parcelId();
        }

        parcelRepository.delete(existingParcel.get());
        return "Посылка успешно удалена";
    }
}
