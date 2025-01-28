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
    private final ParcelRepository parcelRepository;
    private final EditParcelUserCommandValidator commandValidator;

    public String execute(EditParcelUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть отредактирована: \nПередан пустой список аргументов";
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть отредактирована: \n" + String.join("\n", validationErrors);
        }

        var parcelOptional = parcelRepository.findByName(command.currentParcelId());
        if (!parcelOptional.isPresent()) {
            return "Посылка не может быть отредактирована: \nпосылка с названием " + command.currentParcelId() + " не существует";
        }

        var parcel = parcelOptional.get();
        parcel.setName(command.newParcelId());
        parcel.setContent(command.form());
        parcel.setSymbol(command.symbol().charAt(0));
        var savedParcel = parcelRepository.save(parcel);
        if (savedParcel == null) {
            return "Посылка не может быть отредактирована: \nпосылка с названием " + command.currentParcelId() + " не существует";
        }

        return "Посылка успешно отредактирована";
    }
}
