package ru.hofftech.liga.lessons.packageloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;

@Slf4j
@RequiredArgsConstructor
public class PackageService {
    private final FileLoaderService fileLoaderService;
    private final ReportService reportService;

    public void placePackagesFromFileIntoTrucks(String fileName, PlacingAlgorithm algorithm) {
        var packages = fileLoaderService.getPackages(fileName);

        var trucks = LogisticServiceFactory.getLogisticService(algorithm).placePackagesToTrucks(packages);

        log.debug("Получено {} грузовиков", trucks.size());
        for (var truck : trucks) {
            reportService.reportTruckContent(truck.getContent());
        }
    }
}
