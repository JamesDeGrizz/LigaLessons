package ru.hofftech.liga.lessons.packageloader.repository;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Package;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Репозиторий посылок, который управляет хранением и доступом к посылкам.
 * Этот класс предоставляет методы для добавления, поиска, обновления и удаления посылок.
 */
@NoArgsConstructor
public class PackageRepository {
    /**
     * Хранилище посылок, где ключом является имя посылки, а значением - сама посылка.
     */
    private static Map<String, Package> packages;

    static {
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

    /**
     * Конструктор, инициализирующий репозиторий с дополнительными посылками.
     *
     * @param additionalValues дополнительные посылки для инициализации репозитория
     */
    public PackageRepository(Map<String, Package> additionalValues) {
        packages.putAll(additionalValues);
    }

    /**
     * Добавляет новую посылку в репозиторий.
     *
     * @param pkg посылка для добавления
     * @return true, если посылка была успешно добавлена, false, если посылка с таким именем уже существует
     */
    public boolean add(Package pkg) {
        if (packages.containsKey(pkg.getName())) {
            return false;
        }

        packages.put(pkg.getName(), pkg);
        return true;
    }

    /**
     * Находит посылку по имени.
     *
     * @param name имя посылки
     * @return найденная посылка или null, если посылка с таким именем не найдена
     */
    public Optional<Package> find(String name) {
        if (!packages.containsKey(name)) {
            return null;
        }

        return Optional.ofNullable(packages.get(name));
    }

    /**
     * Возвращает список всех посылок в репозитории.
     *
     * @return список всех посылок
     */
    public List<Package> findAll() {
        return packages.values().stream().collect(Collectors.toList());
    }

    /**
     * Обновляет существующую посылку в репозитории.
     *
     * @param packageSourceId имя существующей посылки
     * @param pkg новая посылка для обновления
     * @return true, если посылка была успешно обновлена, false, если посылка с таким именем не найдена
     */
    public boolean update(String packageSourceId, Package pkg) {
        if (!packages.containsKey(packageSourceId)) {
            return false;
        }

        packages.remove(packageSourceId);
        packages.put(pkg.getName(), pkg);
        return true;
    }

    /**
     * Удаляет посылку из репозитория по имени.
     *
     * @param name имя посылки для удаления
     * @return true, если посылка была успешно удалена, false, если посылка с таким именем не найдена
     */
    public boolean delete(String name) {
        if (!packages.containsKey(name)) {
            return false;
        }

        packages.remove(name);
        return true;
    }
}
