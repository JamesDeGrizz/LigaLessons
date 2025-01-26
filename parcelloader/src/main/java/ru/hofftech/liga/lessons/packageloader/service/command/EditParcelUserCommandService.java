package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.EditParcelUserCommandValidator;

/**
 * Сервис для редактирования посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд редактирования посылок.
 */
@AllArgsConstructor
public class EditParcelUserCommandService implements UserCommandService {
    private static final String DELIMITER = ",";

    private final ParcelRepository parcelRepository;
    private final EditParcelUserCommandValidator commandValidator;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть отредактирована: \nПередан пустой список аргументов";
        }
        if (!(command instanceof EditParcelUserCommandDto)) {
            return "Посылка не может быть отредактирована: \nПередана команда неправильного типа";
        }

        var castedCommand = (EditParcelUserCommandDto) command;

        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть отредактирована: \n" + String.join("\n", validationErrors);
        }

        var parcelOptional = parcelRepository.findById(castedCommand.currentParcelId());
        if (!parcelOptional.isPresent()) {
            return "Посылка не может быть отредактирована: \nпосылка с названием " + castedCommand.currentParcelId() + " не существует";
        }

        var parcel = parcelOptional.get();
        parcel.setName(castedCommand.currentParcelId());
        parcel.setContentRawString(castedCommand.form());
        var savedParcel = parcelRepository.save(parcel);
        if (savedParcel == null) {
            return "Посылка не может быть отредактирована: \nпосылка с названием " + castedCommand.currentParcelId() + " не существует";
        }

        return "Посылка успешно отредактирована";
    }
}
