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
    private final ParcelRepository parcelRepository;
    private final DeleteParcelUserCommandValidator commandValidator;

    public String execute(DeleteParcelUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть удалена: \nПередан пустой список аргументов";
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть удалена: \n" + String.join("\n", validationErrors);
        }

        var existingParcel = parcelRepository.findByName(command.parcelId());
        if (!existingParcel.isPresent()) {
            return "Посылка не может быть удалена: \nпосылка с названием \"{}\" не существует" + command.parcelId();
        }

        parcelRepository.delete(existingParcel.get());
        return "Посылка успешно удалена";
    }
}
