package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindPackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

/**
 * Сервис для поиска посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд поиска посылок.
 */
@AllArgsConstructor
public class FindPackageUserCommandService implements UserCommandService {
    private final PackageRepository packageRepository;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (!(command instanceof FindPackageUserCommandDto)) {
            return "Посылка не может быть выведена: \nПередана команда неправильного типа";
        }

        var castedCommand = (FindPackageUserCommandDto) command;

        if (castedCommand.packageId() == null || castedCommand.packageId().isEmpty()) {
            var packages = packageRepository.findAll();
            var stringBuilder = new StringBuilder();
            for (var pkg : packages) {
                stringBuilder.append(pkg.toString());
            }
            return stringBuilder.toString();
        }

        var pkg = packageRepository.find(castedCommand.packageId());
        if (pkg == null || !pkg.isPresent()) {
            return "Посылка не может быть выведена: \nпосылка с названием " + castedCommand.packageId() + " не существует";
        }

        return pkg.get().toString();
    }
}
