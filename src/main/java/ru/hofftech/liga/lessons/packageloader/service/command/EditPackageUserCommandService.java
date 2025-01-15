package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.EditPackageUserCommandValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Сервис для редактирования посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд редактирования посылок.
 */
@AllArgsConstructor
public class EditPackageUserCommandService implements UserCommandService {
    private static final String ARGUMENT_ID = "-id";
    private static final String ARGUMENT_NAME = "-name";
    private static final String ARGUMENT_SYMBOL = "-symbol";
    private static final String ARGUMENT_FORM = "-form";

    private final PackageRepository packageRepository;
    private final EditPackageUserCommandValidator commandValidator;

    @Override
    public String execute(Map<String, String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            return "Посылка не может быть отредактирована: \nПередан пустой список аргументов";
        }
        var validationErrors = commandValidator.validate(arguments);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть отредактирована: \n" + String.join("\n", validationErrors);
        }

        var packageSourceId = getPackageSourceId(arguments);
        var packageName = getPackageName(arguments);
        var packageSymbol = getPackageSymbol(arguments);
        var packageContent = getPackageContent(arguments);

        if (!packageRepository.update(packageSourceId, new Package(packageContent, packageName, packageSymbol))) {
            return "Посылка не может быть отредактирована: \nпосылка с названием " + packageName + " не существует";
        }

        return "Посылка успешно отредактирована";
    }

    private String getPackageSourceId(Map<String, String> arguments) {
        return arguments.get(ARGUMENT_ID);
    }

    private String getPackageName(Map<String, String> arguments) {
        return arguments.get(ARGUMENT_NAME);
    }

    private char getPackageSymbol(Map<String, String> arguments) {
        return arguments.get(ARGUMENT_SYMBOL).charAt(0);
    }

    private List<String> getPackageContent(Map<String, String> arguments) {
        var form = arguments.get(ARGUMENT_FORM).replaceAll("\\\\n", "\n");
        return Arrays.stream(form.split("\n")).toList();
    }
}
