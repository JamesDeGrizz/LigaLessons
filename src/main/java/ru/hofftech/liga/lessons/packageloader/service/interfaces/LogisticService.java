package ru.hofftech.liga.lessons.packageloader.service.interfaces;

import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.util.List;

public interface LogisticService {
    int TRUCK_MAX_WIDTH = 6;
    int TRUCK_MAX_HEIGHT = 6;

    List<Truck> placePackagesToTrucks(List<Package> packages, int trucksCount);
}
