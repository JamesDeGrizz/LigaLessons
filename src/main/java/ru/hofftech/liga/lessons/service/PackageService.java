package ru.hofftech.liga.lessons.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.model.PlacingAlgorithm;
import ru.hofftech.liga.lessons.repository.FileRepository;

@Slf4j
@RequiredArgsConstructor
public class PackageService {
    private final FileRepository fileRepository;

    public void placePackagesFromFileIntoTrucks(String fileName, PlacingAlgorithm algorithm) {
        var packages = fileRepository.getPackages(fileName);

        LogisticServiceFactory.getLogisticService(algorithm)
                .placePackagesToTrucks(packages);
    }
}
