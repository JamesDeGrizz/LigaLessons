package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Сервис для создания посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд создания посылок.
 */
@AllArgsConstructor
public class CreatePackageUserCommandService implements UserCommandService {
    private static final String ARGUMENT_NAME = "-name";
    private static final String ARGUMENT_SYMBOL = "-symbol";
    private static final String ARGUMENT_FORM = "-form";

    /**
     * Репозиторий посылок, используемый для хранения и управления посылками.
     */
    private final PackageRepository packageRepository;
    /**
     * Список ошибок, возникающих при выполнении команды.
     */
    private final List<String> errors = new ArrayList<>();

    /**
     * Выполняет команду создания посылки на основе переданных аргументов.
     *
     * @param arguments аргументы команды
     * @return сообщение о результате выполнения команды
     */
    @Override
    public String execute(Map<String, String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            return "Посылка не может быть создана: \nПередан пустой список аргументов";
        }

        var packageName = getPackageName(arguments);
        var packageSymbol = getPackageSymbol(arguments);
        var packageContent = getPackageContent(arguments);

        if (!errors.isEmpty()) {
            var errorMessage = "Посылка не может быть создана: \n" + String.join("\n", errors);
            errors.clear();
            return errorMessage;
        }

        if (!packageRepository.add(new Package(packageContent, packageName, packageSymbol))) {
            return "Посылка не может быть создана: \nпосылка с названием " + packageName + " уже существует";
        }

        return "Посылка успешно создана";
    }

    private String getPackageName(Map<String, String> arguments) {
        if (!arguments.containsKey(ARGUMENT_NAME)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_NAME + "\"");
            return null;
        }

        return arguments.get(ARGUMENT_NAME);
    }

    private char getPackageSymbol(Map<String, String> arguments) {
        if (!arguments.containsKey(ARGUMENT_SYMBOL)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_SYMBOL + "\"");
            return 0;
        }

        return arguments.get(ARGUMENT_SYMBOL).charAt(0);
    }

    private List<String> getPackageContent(Map<String, String> arguments) {
        if (!arguments.containsKey(ARGUMENT_FORM)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_FORM + "\"");
            return null;
        }

        var form = arguments.get(ARGUMENT_FORM).replaceAll("\\\\n", "\n");
        return Arrays.stream(form.split("\n")).toList();
    }
}
