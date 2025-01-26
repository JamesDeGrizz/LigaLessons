package ru.hofftech.liga.lessons.consoleclient.service.interfaces;

import ru.hofftech.liga.lessons.consoleclient.model.Parcel;
import ru.hofftech.liga.lessons.consoleclient.model.Truck;
import ru.hofftech.liga.lessons.consoleclient.model.TruckSize;

import java.util.List;

/**
 * Интерфейс для логистического сервиса, который занимается размещением посылок на грузовиках.
 * Реализации этого интерфейса должны предоставлять методы для размещения посылок на грузовиках в соответствии с заданными размерами грузовиков.
 */
public interface LogisticService {
    /**
     * Размещает посылки на грузовиках в соответствии с заданными размерами грузовиков.
     *
     * @param parcels список посылок для размещения
     * @param truckSizes список размеров грузовиков
     * @return список грузовиков с размещенными посылками
     */
    List<Truck> placeParcelsToTrucks(List<Parcel> parcels, List<TruckSize> truckSizes);
}
