package ru.hofftech.liga.lessons.packageloader.service.interfaces;

import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;

import java.util.List;

/**
 * Интерфейс для логистического сервиса, который занимается размещением посылок на грузовиках.
 * Реализации этого интерфейса должны предоставлять методы для размещения посылок на грузовиках в соответствии с заданными размерами грузовиков.
 */
public interface LogisticService {
    /**
     * Размещает посылки на грузовиках в соответствии с заданными размерами грузовиков.
     *
     * @param packages список посылок для размещения
     * @param truckSizes список размеров грузовиков
     * @return список грузовиков с размещенными посылками
     */
    List<Truck> placePackagesToTrucks(List<Package> packages, List<TruckSize> truckSizes);
}
