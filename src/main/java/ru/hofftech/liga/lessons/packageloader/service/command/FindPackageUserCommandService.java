package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Map;

@AllArgsConstructor
public class FindPackageUserCommandService implements UserCommandService {
    private final PackageRepository packageRepository;

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
