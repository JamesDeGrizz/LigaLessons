package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditPackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.EditPackageUserCommandValidator;

import java.util.Arrays;
import java.util.List;

/**
 * Сервис для редактирования посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд редактирования посылок.
 */
@AllArgsConstructor
public class EditPackageUserCommandService implements UserCommandService {
    private final PackageRepository packageRepository;
    private final EditPackageUserCommandValidator commandValidator;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть отредактирована: \nПередан пустой список аргументов";
        }
        if (!(command instanceof EditPackageUserCommandDto)) {
            return "Посылка не может быть отредактирована: \nПередана команда неправильного типа";
        }

        var castedCommand = (EditPackageUserCommandDto) command;

        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть отредактирована: \n" + String.join("\n", validationErrors);
        }

        var packageContent = getPackageContent(castedCommand.form());

        if (!packageRepository.update(castedCommand.currentPackageId(), new Package(packageContent, castedCommand.newPackageId(), castedCommand.symbol().charAt(0)))) {
            return "Посылка не может быть отредактирована: \nпосылка с названием " + castedCommand.currentPackageId() + " не существует";
        }

        return "Посылка успешно отредактирована";
    }

    private List<String> getPackageContent(String form) {
        return Arrays.stream(form.split(",")).toList();
    }
}
