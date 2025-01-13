package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Map;

/**
 * Сервис для поиска посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд поиска посылок.
 */
@AllArgsConstructor
public class FindPackageUserCommandService implements UserCommandService {
    /**
     * Репозиторий посылок, используемый для хранения и управления посылками.
     */
    private final PackageRepository packageRepository;

    /**
     * Выполняет команду поиска посылок на основе переданных аргументов.
     *
     * @param arguments аргументы команды
     * @return строковое представление найденных посылок или сообщение об ошибке
     */
    @Override
    public String execute(Map<String, String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            var packages = packageRepository.findAll();
            var sb = new StringBuilder();
            for (var pkg : packages) {
                sb.append(pkg.toString());
            }
            return sb.toString();
        }

        var packageName = getPackageName(arguments);

        var pkg = packageRepository.find(packageName);
        if (pkg == null) {
            return "Посылка не может быть выведена: \nпосылка с названием " + packageName + " не существует";
        }

        return pkg.toString();
    }

    private String getPackageName(Map<String, String> arguments) {
        return arguments.keySet().stream().findFirst().get();
    }
}
