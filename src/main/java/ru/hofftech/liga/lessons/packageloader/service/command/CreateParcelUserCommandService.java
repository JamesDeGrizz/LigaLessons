package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.CreateParcelUserCommandValidator;

import java.util.Arrays;
import java.util.List;

/**
 * Сервис для создания посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд создания посылок.
 */
@AllArgsConstructor
public class CreateParcelUserCommandService implements UserCommandService {
    private static final String DELIMITER = ",";

    private final ParcelRepository parcelRepository;
    private final CreateParcelUserCommandValidator commandValidator;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть создана: \nПередан пустой список аргументов";
        }
        if (!(command instanceof CreateParcelUserCommandDto)) {
            return "Посылка не может быть создана: \nПередана команда неправильного типа";
        }

        var castedCommand = (CreateParcelUserCommandDto) command;

        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть создана: \n" + String.join("\n", validationErrors);
        }

        var parcelContent = getParcelContent(castedCommand.form());

        if (!parcelRepository.add(new Parcel(parcelContent, castedCommand.parcelId(), castedCommand.symbol().charAt(0)))) {
            return "Посылка не может быть создана: \nпосылка с названием " + castedCommand.parcelId() + " уже существует";
        }

        return "Посылка успешно создана";
    }

    private List<String> getParcelContent(String form) {
        return Arrays.stream(form.split(DELIMITER)).toList();
    }
}
