package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.DeleteParcelUserCommandValidator;

/**
 * Сервис для удаления посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд удаления посылок.
 */
@AllArgsConstructor
public class DeleteParcelUserCommandService implements UserCommandService {
    private final ParcelRepository parcelRepository;
    private final DeleteParcelUserCommandValidator commandValidator;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть удалена: \nПередан пустой список аргументов";
        }
        if (!(command instanceof DeleteParcelUserCommandDto)) {
            return "Посылка не может быть удалена: \nПередана команда неправильного типа";
        }

        var castedCommand = (DeleteParcelUserCommandDto) command;
        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть удалена: \n" + String.join("\n", validationErrors);
        }

        var existingParcel = parcelRepository.findById(castedCommand.parcelId());
        if (!existingParcel.isPresent()) {
            return "Посылка не может быть удалена: \nпосылка с названием \"{}\" не существует" + castedCommand.parcelId();
        }

        parcelRepository.delete(existingParcel.get());
        return "Посылка успешно удалена";
    }
}
