package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

import java.util.ArrayList;

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
        String reportFileName = null;
        if (needToSaveReport) {
            reportFileName = userConsoleService.getReportFileName();
        }

        var trucks = fileLoaderService.getTrucks(userConsoleService.getFileName());
        var packages = new ArrayList<ru.hofftech.liga.lessons.packageloader.model.Package>();
        for (var truck : trucks) {
            var truckService = truckServiceFactory.getTruckService(truck);
            packages.addAll(truckService.getPackages());
        }

        reportService.reportPackages(packages);

        if (needToSaveReport) {
            reportService.savePackagesToFile(reportFileName, packages);
        }
    }
}
