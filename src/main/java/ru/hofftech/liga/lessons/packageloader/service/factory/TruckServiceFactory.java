package ru.hofftech.liga.lessons.packageloader.service.factory;

import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;

public class TruckServiceFactory {
    public TruckService getTruckService(TruckSize truckSize) {
        return new TruckService(truckSize);
    }

    public TruckService getTruckService(Truck truck) {
        return new TruckService(truck);
    }
}
