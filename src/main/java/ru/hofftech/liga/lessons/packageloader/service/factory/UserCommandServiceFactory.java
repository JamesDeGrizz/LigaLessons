package ru.hofftech.liga.lessons.packageloader.service.factory;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.service.command.ExitUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.command.HelpUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.command.ProceedPackagesUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.RetryUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.ProceedTrucksUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.UserHelpService;

@AllArgsConstructor
public class UserCommandServiceFactory {
    private final UserHelpService userHelpService;
    private final UserConsoleService userConsoleService;
    private final FileLoaderService fileLoaderService;
    private final ReportService reportService;
    private final LogisticServiceFactory logisticServiceFactory;
    private final TruckServiceFactory truckServiceFactory;

    public UserCommandService getUserCommandService(Command command) {
        return switch (command) {
            case Exit -> new ExitUserCommandService();
            case Retry -> new RetryUserCommandService();
            case Help -> new HelpUserCommandService(userHelpService);
            case ProceedPackages -> new ProceedPackagesUserCommandService(userConsoleService, fileLoaderService, reportService, logisticServiceFactory);
            case ProceedTrucks -> new ProceedTrucksUserCommandService(userConsoleService, fileLoaderService, reportService, truckServiceFactory);
        };
    }
}
