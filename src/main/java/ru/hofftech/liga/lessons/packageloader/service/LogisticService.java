package ru.hofftech.liga.lessons.packageloader.service;

import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.util.List;

public interface LogisticService {
    List<Truck> placePackagesToTrucks(List<Package> packages);
}
