package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeletePackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.DeletePackageUserCommandValidator;

/**
 * Сервис для удаления посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд удаления посылок.
 */
@AllArgsConstructor
public class DeletePackageUserCommandService implements UserCommandService {
    private final PackageRepository packageRepository;
    private final DeletePackageUserCommandValidator commandValidator;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть удалена: \nПередан пустой список аргументов";
        }
        if (!(command instanceof DeletePackageUserCommandDto)) {
            return "Посылка не может быть удалена: \nПередана команда неправильного типа";
        }

        var castedCommand = (DeletePackageUserCommandDto) command;
        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть удалена: \n" + String.join("\n", validationErrors);
        }

        if (!packageRepository.delete(castedCommand.packageId())) {
            return "Посылка не может быть удалена: \nпосылка с названием \"{}\" не существует" + castedCommand.packageId();
        }

        return "Посылка успешно удалена";
    }
}
