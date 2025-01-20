package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreatePackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.CreatePackageUserCommandValidator;

import java.util.Arrays;
import java.util.List;

/**
 * Сервис для создания посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд создания посылок.
 */
@AllArgsConstructor
public class CreatePackageUserCommandService implements UserCommandService {
    private final PackageRepository packageRepository;
    private final CreatePackageUserCommandValidator commandValidator;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть создана: \nПередан пустой список аргументов";
        }
        if (!(command instanceof CreatePackageUserCommandDto)) {
            return "Посылка не может быть создана: \nПередана команда неправильного типа";
        }

        var castedCommand = (CreatePackageUserCommandDto) command;

        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть создана: \n" + String.join("\n", validationErrors);
        }

        var packageContent = getPackageContent(castedCommand.form());

        if (!packageRepository.add(new Package(packageContent, castedCommand.packageId(), castedCommand.symbol().charAt(0)))) {
            return "Посылка не может быть создана: \nпосылка с названием " + castedCommand.packageId() + " уже существует";
        }

        return "Посылка успешно создана";
    }

    private List<String> getPackageContent(String form) {
        return Arrays.stream(form.split(",")).toList();
    }
}
