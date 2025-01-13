package ru.hofftech.liga.lessons.packageloader.repository;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class PackageRepository {
    private Map<String, Package> packages;

    public PackageRepository() {
        packages = new HashMap<>();
        packages.put("Посылка тип 1", new Package(List.of("1"), "Посылка тип 1", '1'));
        packages.put("Посылка тип 2", new Package(List.of("22"), "Посылка тип 2", '2'));
        packages.put("Посылка тип 3", new Package(List.of("333"), "Посылка тип 3", '3'));
        packages.put("Посылка тип 4", new Package(List.of("4444"), "Посылка тип 4", '4'));
        packages.put("Посылка тип 5", new Package(List.of("55555"), "Посылка тип 5", '5'));
        packages.put("Посылка тип 6", new Package(List.of("666", "666"), "Посылка тип 6", '6'));
        packages.put("Посылка тип 7", new Package(List.of("777", "7777"), "Посылка тип 7", '7'));
        packages.put("Посылка тип 8", new Package(List.of("8888", "8888"), "Посылка тип 8", '8'));
        packages.put("Посылка тип 9", new Package(List.of("999", "999", "999"), "Посылка тип 9", '9'));
    }

    public PackageRepository(Map<String, Package> additionalValues) {
        this();
        for (var additionalPackage : additionalValues.entrySet()) {
            packages.put(additionalPackage.getKey(), additionalPackage.getValue());
        }
    }

    public boolean add(Package pkg) {
        if (packages.containsKey(pkg.getName())) {
            return false;
        }

        packages.put(pkg.getName(), pkg);
        return true;
    }

    public Package find(String name) {
        if (!packages.containsKey(name)) {
            return null;
        }

        return packages.get(name);
    }

    public List<Package> findAll() {
        return packages.values().stream().collect(Collectors.toList());
    }

    public boolean update(String packageSourceId, Package pkg) {
        if (!packages.containsKey(packageSourceId)) {
            return false;
        }

        packages.remove(packageSourceId);
        packages.put(pkg.getName(), pkg);
        return true;
    }

    public boolean delete(String name) {
        if (!packages.containsKey(name)) {
            return false;
        }

        packages.remove(name);
        return true;
    }
}
