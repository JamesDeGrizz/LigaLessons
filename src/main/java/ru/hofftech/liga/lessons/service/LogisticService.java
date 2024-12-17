package ru.hofftech.liga.lessons.service;

import ru.hofftech.liga.lessons.model.Package;

import java.util.List;

public interface LogisticService {
    void placePackagesToTrucks(List<Package> packages);
}
