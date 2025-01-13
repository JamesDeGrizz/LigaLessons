package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Map;

@AllArgsConstructor
public class DeletePackageUserCommandService implements UserCommandService {
    private final PackageRepository packageRepository;

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
