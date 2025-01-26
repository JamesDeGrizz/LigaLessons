package ru.hofftech.liga.lessons.telegramclient.service.logistic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.telegramclient.model.Parcel;
import ru.hofftech.liga.lessons.telegramclient.model.Truck;
import ru.hofftech.liga.lessons.telegramclient.model.TruckSize;
import ru.hofftech.liga.lessons.telegramclient.service.TruckService;
import ru.hofftech.liga.lessons.telegramclient.service.interfaces.LogisticService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Сервис для равномерного распределения посылок по грузовикам.
 * Этот класс реализует интерфейс {@link LogisticService} и предоставляет методы для равномерного распределения посылок по грузовикам.
 */
@Slf4j
@AllArgsConstructor
public class BalancedFillTruckLogisticService implements LogisticService {
    private final TruckService truckService;

    /**
     * Размещает посылки в грузовиках методом "равномерная погрузка по машинам".
     *
     * @param parcels список посылок для размещения
     * @param truckSizes список размеров грузовиков
     * @return список грузовиков с размещенными посылками
     */
    @Override
    public List<Truck> placeParcelsToTrucks(List<Parcel> parcels, List<TruckSize> truckSizes) {
        log.info("Начинаем заполнение грузовиков методом \"равномерная погрузка по машинам\"");
        var trucks = getTrucks(truckSizes);

        distributePackagesToTrucks(parcels, trucks);

        log.info("Заполнение грузовиков методом \"равномерная погрузка по машинам\" успешно завершено");
        return trucks;
    }

    /**
     * Получает список грузовов на основе их размеров.
     *
     * @param truckSizes список размеров грузовиков
     * @return список сервисов для работы с грузовиками
     */
    private List<Truck> getTrucks(List<TruckSize> truckSizes) {
        var trucks = new ArrayList<Truck>();
        for (var truckSize : truckSizes) {
            trucks.add(new Truck(truckSize));
        }
        return trucks;
    }

    /**
     * Распределяет посылки по грузовикам.
     *
     * @param parcels список посылок для размещения
     * @param trucks список грузовиков
     */
    private void distributePackagesToTrucks(List<Parcel> parcels, List<Truck> trucks) {
        for (var parcel : parcels) {
            var mostFreeTruck = trucks.stream()
                    .max(Comparator.comparing(truck -> truckService.getFreeSpaceCount(truck)))
                    .get();

            var placed = false;
            for (int i = 0; i <= mostFreeTruck.getSize().getHeight() - parcel.getHeight(); i++) {
                if (placed) {
                    break;
                }

                for (int j = 0; j <= mostFreeTruck.getSize().getHeight() - parcel.getWidth(); j++) {
                    if (truckService.canPlaceParcel(mostFreeTruck, parcel, i, j)) {
                        truckService.placeParcel(mostFreeTruck, parcel, i, j);
                        placed = true;
                        break;
                    }
                }
            }

            if (!placed) {
                throw new IllegalArgumentException("Посылки невозможно отправить заданным алгоритмом, размер посылок превышает ёмкость грузовиков.");
            }

            log.info("Посылка \n{} успешно погружена в грузовик", parcel);
        }
    }
}
