package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.EditParcelUserCommandValidator;

import java.util.Arrays;
import java.util.List;

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

        var parcelContent = getParcelContent(castedCommand.form());

        if (!parcelRepository.update(castedCommand.currentParcelId(), new Parcel(parcelContent, castedCommand.newParcelId(), castedCommand.symbol().charAt(0)))) {
            return "Посылка не может быть отредактирована: \nпосылка с названием " + castedCommand.currentParcelId() + " не существует";
        }

        return "Посылка успешно отредактирована";
    }

    private List<String> getParcelContent(String form) {
        return Arrays.stream(form.split(DELIMITER)).toList();
    }
}
