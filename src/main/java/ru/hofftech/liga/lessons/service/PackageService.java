package ru.hofftech.liga.lessons.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.model.PlacingAlgorithm;
import ru.hofftech.liga.lessons.model.Truck;
import ru.hofftech.liga.lessons.repository.FileRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PackageService {
    private final FileRepository fileRepository;

    public List<Truck> placePackagesFromFileIntoTrucks(String fileName, PlacingAlgorithm algorithm) {
        var packages = fileRepository.getPackages(fileName);

        return LogisticServiceFactory.getLogisticService(algorithm)
                .placePackagesToTrucks(packages);
    }
}
