package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.factory.LogisticServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class ProceedPackagesUserCommandService implements UserCommandService {
    private final UserConsoleService userConsoleService;
    private final FileLoaderService fileLoaderService;
    private final ReportService reportService;
    private final LogisticServiceFactory logisticServiceFactory;

    @Override
    public void execute() {
        var needToSaveReport = userConsoleService.needSaveToFile();
        var reportFileName = getReportFileName(needToSaveReport);

        var packages = fileLoaderService.getPackages(userConsoleService.getFileName());
        if (packages.isEmpty()) {
            log.error("Не удалось загрузить посылки из файла {}", userConsoleService.getFileName());
            return;
        }

        var trucks = placePackagesToTrucks(packages);
        if (trucks.isEmpty()) {
            return;
        }

        reportTruckContent(trucks);

        saveReportToFileIfNeed(needToSaveReport, reportFileName, trucks);
    }

    private String getReportFileName(boolean needToSaveReport) {
        if (!needToSaveReport) {
            return "";
        }
        return userConsoleService.getReportFileName();
    }

    private List<Truck> placePackagesToTrucks(List<Package> packages) {
        List<Truck> trucks;
        try {
            var logisticService = logisticServiceFactory.getLogisticService(userConsoleService.getAlgorithm());
            trucks = logisticService.placePackagesToTrucks(packages, userConsoleService.getTrucksCount());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }

        return trucks;
    }

    private void reportTruckContent(List<Truck> trucks) {
        log.debug("Получено {} грузовиков", trucks.size());
        for (var truck : trucks) {
            reportService.reportTruckContent(truck.getContent());
        }
    }

    private void saveReportToFileIfNeed(boolean needToSaveReport, String reportFileName, List<Truck> trucks) {
        if (needToSaveReport) {
            reportService.saveTrucksToFile(reportFileName, trucks);
        }
    }
}
