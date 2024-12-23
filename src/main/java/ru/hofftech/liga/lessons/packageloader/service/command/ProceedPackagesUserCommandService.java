package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.factory.LogisticServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

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
        String reportFileName = null;
        if (needToSaveReport) {
            reportFileName = userConsoleService.getReportFileName();
        }

        var packages = fileLoaderService.getPackages(userConsoleService.getFileName());

        List<Truck> trucks = null;
        try {
            trucks = logisticServiceFactory.getLogisticService(userConsoleService.getAlgorithm())
                    .placePackagesToTrucks(packages, userConsoleService.getTrucksCount());
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }

        log.debug("Получено {} грузовиков", trucks.size());
        for (var truck : trucks) {
            reportService.reportTruckContent(truck.getContent());
        }

        if (needToSaveReport) {
            reportService.saveTrucksToFile(reportFileName, trucks);
        }
    }
}
