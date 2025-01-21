package ru.hofftech.liga.lessons.packageloader.repository;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;

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
public class ParcelRepository {
    /**
     * Хранилище посылок, где ключом является имя посылки, а значением - сама посылка.
     */
    private Map<String, Parcel> parcels = new HashMap<>() {{
            put("тип 1", new Parcel(List.of("1"), "тип 1", '1'));
            put("тип 2", new Parcel(List.of("22"), "тип 2", '2'));
            put("тип 3", new Parcel(List.of("333"), "тип 3", '3'));
            put("тип 4", new Parcel(List.of("4444"), "тип 4", '4'));
            put("тип 5", new Parcel(List.of("55555"), "тип 5", '5'));
            put("тип 6", new Parcel(List.of("666", "666"), "тип 6", '6'));
            put("тип 7", new Parcel(List.of("777", "7777"), "тип 7", '7'));
            put("тип 8", new Parcel(List.of("8888", "8888"), "тип 8", '8'));
            put("тип 9", new Parcel(List.of("999", "999", "999"), "тип 9", '9'));
    }};

    /**
     * Конструктор, инициализирующий репозиторий с дополнительными посылками.
     *
     * @param additionalValues дополнительные посылки для инициализации репозитория
     */
    public ParcelRepository(Map<String, Parcel> additionalValues) {
        parcels = new HashMap<>();
        parcels.putAll(additionalValues);
    }

    /**
     * Добавляет новую посылку в репозиторий.
     *
     * @param parcel посылка для добавления
     * @return true, если посылка была успешно добавлена, false, если посылка с таким именем уже существует
     */
    public boolean add(Parcel parcel) {
        if (parcels.containsKey(parcel.getName())) {
            return false;
        }

        parcels.put(parcel.getName(), parcel);
        return true;
    }

    /**
     * Находит посылку по имени.
     *
     * @param name имя посылки
     * @return найденная посылка или null, если посылка с таким именем не найдена
     */
    public Optional<Parcel> find(String name) {
        if (!parcels.containsKey(name)) {
            return null;
        }

        return Optional.ofNullable(parcels.get(name));
    }

    /**
     * Возвращает список всех посылок в репозитории.
     *
     * @return список всех посылок
     */
    public List<Parcel> findAll() {
        return parcels.values().stream().collect(Collectors.toList());
    }

    /**
     * Обновляет существующую посылку в репозитории.
     *
     * @param parcelSourceId имя существующей посылки
     * @param parcel новая посылка для обновления
     * @return true, если посылка была успешно обновлена, false, если посылка с таким именем не найдена
     */
    public boolean update(String parcelSourceId, Parcel parcel) {
        if (!parcels.containsKey(parcelSourceId)) {
            return false;
        }

        parcels.remove(parcelSourceId);
        parcels.put(parcel.getName(), parcel);
        return true;
    }

    /**
     * Удаляет посылку из репозитория по имени.
     *
     * @param name имя посылки для удаления
     * @return true, если посылка была успешно удалена, false, если посылка с таким именем не найдена
     */
    public boolean delete(String name) {
        if (!parcels.containsKey(name)) {
            return false;
        }

        parcels.remove(name);
        return true;
    }
}
