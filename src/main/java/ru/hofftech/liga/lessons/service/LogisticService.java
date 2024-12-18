package ru.hofftech.liga.lessons.service;

import ru.hofftech.liga.lessons.model.Package;
import ru.hofftech.liga.lessons.model.Truck;

import java.util.List;

public interface LogisticService {
    List<Truck> placePackagesToTrucks(List<Package> packages);
}
