package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.validator.EditParcelUserCommandValidator;

/**
 * Сервис для редактирования посылок на основе команд пользователя.
 */
@AllArgsConstructor
public class EditParcelUserCommandService {
    private static final String ERROR_MESSAGE_TEXT = "Посылка не может быть отредактирована: ";

    private final ParcelRepository parcelRepository;
    private final EditParcelUserCommandValidator commandValidator;

    public String execute(EditParcelUserCommandDto command) {
        if (command == null) {
            return ERROR_MESSAGE_TEXT + "\nПередан пустой список аргументов";
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            return ERROR_MESSAGE_TEXT + "\n" + String.join("\n", validationErrors);
        }

        var parcelOptional = parcelRepository.findByName(command.currentParcelId());
        if (parcelOptional.isEmpty()) {
            return ERROR_MESSAGE_TEXT + "\nпосылка с названием " + command.currentParcelId() + " не существует";
        }

        var parcel = parcelOptional.get();
        parcel.setName(command.newParcelId());
        parcel.setContent(command.form());
        parcel.setSymbol(command.symbol().charAt(0));
        var savedParcel = parcelRepository.save(parcel);
        if (savedParcel == null) {
            return ERROR_MESSAGE_TEXT + "\nпосылка с названием " + command.currentParcelId() + " не существует";
        }

        return "Посылка успешно отредактирована";
    }
}
