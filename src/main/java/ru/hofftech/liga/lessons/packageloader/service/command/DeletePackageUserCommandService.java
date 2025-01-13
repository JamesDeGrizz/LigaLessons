package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Map;

/**
 * Сервис для удаления посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд удаления посылок.
 */
@AllArgsConstructor
public class DeletePackageUserCommandService implements UserCommandService {
    /**
     * Репозиторий посылок, используемый для хранения и управления посылками.
     */
    private final PackageRepository packageRepository;

    /**
     * Выполняет команду удаления посылки на основе переданных аргументов.
     *
     * @param arguments аргументы команды
     * @return сообщение о результате выполнения команды
     */
    @Override
    public String execute(Map<String, String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            return "Посылка не может быть удалена: \nПередан пустой список аргументов";
        }

        var packageName = getPackageName(arguments);

        if (!packageRepository.delete(packageName)) {
            return "Посылка не может быть удалена: \nпосылка с названием \"{}\" не существует" + packageName;
        }

        return "Посылка успешно удалена";
    }

    private String getPackageName(Map<String, String> arguments) {
        return arguments.keySet().stream().findFirst().get();
    }
}
