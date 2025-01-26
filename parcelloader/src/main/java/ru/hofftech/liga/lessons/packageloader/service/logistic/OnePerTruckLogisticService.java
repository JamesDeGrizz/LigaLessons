package ru.hofftech.liga.lessons.packageloader.service.logistic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.LogisticService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для заполнения грузовиков методом "один грузовик = максимум посылок".
 * Этот класс реализует интерфейс {@link LogisticService} и предоставляет методы для заполнения грузовиков посылками,
 * стараясь разместить максимальное количество посылок в каждом грузовике.
 */
@Slf4j
@AllArgsConstructor
public class OnePerTruckLogisticService implements LogisticService {
    private final TruckService truckService;

    /**
     * Размещает посылки в грузовиках методом "один грузовик = максимум посылок".
     *
     * @param parcels список посылок для размещения
     * @param truckSizes список размеров грузовиков
     * @return список грузовиков с размещенными посылками
     */
    @Override
    public List<Truck> placeParcelsToTrucks(List<Parcel> parcels, List<TruckSize> truckSizes) {
        if (parcels.size() > truckSizes.size()) {
            throw new IllegalArgumentException("Посылки невозможно отправить заданным алгоритмом, количество посылок превышает количество грузовиков.");
        }

        log.info("Начинаем заполнение грузовиков методом \"один грузовик = одна посылка\"");

        var trucks = getTrucks(truckSizes);
        placePackages(parcels, trucks);

        log.info("Заполнение грузовиков методом \"один грузовик = одна посылка\" успешно завершено");
        return trucks;
    }

    /**
     * Получает список грузовиков на основе их размеров.
     *
     * @param truckSizes список размеров грузовиков
     * @return список сервисов для работы с грузовиками
     */
    private List<Truck> getTrucks(List<TruckSize> truckSizes) {
        var sortedSizes = truckSizes.stream()
                .sorted((p1, p2) -> p1.getWidth() * p1.getHeight() - p2.getWidth() * p2.getHeight())
                .collect(Collectors.toList());

        var trucks = new ArrayList<Truck>();
        for (var truckSize : sortedSizes) {
            trucks.add(new Truck(truckSize));
        }
        return trucks;
    }

    /**
     * Заполняет грузовики посылками, по одной на грузовик.
     *
     * @param parcels список посылок для размещения
     * @param trucks список грузовиков
     */
    private void placePackages(List<Parcel> parcels, List<Truck> trucks) {
        var trucksArray = trucks.toArray(new Truck[trucks.size()]);
        var packagesArray = parcels.toArray(new Parcel[parcels.size()]);

        for (var i = 0; i < parcels.size(); i++) {
            truckService.placeParcel(trucksArray[i], packagesArray[i], 0, 0);
            log.info("Посылка \n{} успешно погружена в грузовик", packagesArray[i]);
        }
    }
}
