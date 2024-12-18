package ru.hofftech.liga.lessons.packageloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PackageService {
    private final FileLoaderService fileLoaderService;

    public List<Truck> placePackagesFromFileIntoTrucks(String fileName, PlacingAlgorithm algorithm) {
        var packages = fileLoaderService.getPackages(fileName);

        return LogisticServiceFactory.getLogisticService(algorithm)
                .placePackagesToTrucks(packages);
    }
}
