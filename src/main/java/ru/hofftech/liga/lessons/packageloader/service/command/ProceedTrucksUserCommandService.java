package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class ProceedTrucksUserCommandService implements UserCommandService {
    private final UserConsoleService userConsoleService;
    private final FileLoaderService fileLoaderService;
    private final ReportService reportService;
    private final TruckServiceFactory truckServiceFactory;

    @Override
    public void execute() {
        var needToSaveReport = userConsoleService.needSaveToFile();
        var reportFileName = getReportFileName(needToSaveReport);

        var trucks = fileLoaderService.getTrucks(userConsoleService.getFileName());
        if (trucks.isEmpty()) {
            log.error("Не удалось загрузить грузовики из файла {}", userConsoleService.getFileName());
            return;
        }

        var packages = getPackagesFromTrucks(trucks);

        reportService.reportPackages(packages);

        saveReportToFileIfNeed(needToSaveReport, reportFileName, packages);
    }

    private String getReportFileName(boolean needToSaveReport) {
        if (!needToSaveReport) {
            return "";
        }
        return userConsoleService.getReportFileName();
    }

    private List<Package> getPackagesFromTrucks(List<Truck> trucks) {
        var packages = new ArrayList<Package>();
        for (var truck : trucks) {
            var truckService = truckServiceFactory.getTruckService(truck);
            packages.addAll(truckService.getPackages());
        }
        return packages;
    }

    private void saveReportToFileIfNeed(boolean needToSaveReport, String reportFileName, List<Package> packages) {
        if (needToSaveReport) {
            reportService.savePackagesToFile(reportFileName, packages);
        }
    }
}
