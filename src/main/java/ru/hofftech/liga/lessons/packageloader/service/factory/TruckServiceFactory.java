package ru.hofftech.liga.lessons.packageloader.service.factory;

import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;

public class TruckServiceFactory {
    public TruckService getTruckService(int width, int height) {
        return new TruckService(width, height);
    }

    public TruckService getTruckService(Truck truck) {
        return new TruckService(truck);
    }
}
