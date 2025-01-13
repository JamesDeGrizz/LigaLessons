package ru.hofftech.liga.lessons.packageloader.service.interfaces;

import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;

import java.util.List;

public interface LogisticService {
    List<Truck> placePackagesToTrucks(List<Package> packages, List<TruckSize> truckSizes);
}
