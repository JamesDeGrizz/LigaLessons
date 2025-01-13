package ru.hofftech.liga.lessons.packageloader.service.factory;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.command.CreatePackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.DeletePackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.EditPackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.ExitUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindPackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.HelpUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadPackagesUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.RetryUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.UnloadTrucksUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

@AllArgsConstructor
public class UserCommandServiceFactory {
    private final FileLoaderService fileLoaderService;
    private final ReportService reportService;
    private final LogisticServiceFactory logisticServiceFactory;
    private final TruckServiceFactory truckServiceFactory;
    private final PackageRepository packageRepository;

    public UserCommandService getUserCommandService(Command command) {
        return switch (command) {
            case Exit -> new ExitUserCommandService();
            case Retry -> new RetryUserCommandService();
            case Help -> new HelpUserCommandService();
            case LoadPackages -> new LoadPackagesUserCommandService(fileLoaderService, reportService, logisticServiceFactory, packageRepository);
            case UnloadTrucks -> new UnloadTrucksUserCommandService(fileLoaderService, reportService, truckServiceFactory);
            case CreatePackage -> new CreatePackageUserCommandService(packageRepository);
            case FindPackage -> new FindPackageUserCommandService(packageRepository);
            case EditPackage -> new EditPackageUserCommandService(packageRepository);
            case DeletePackage -> new DeletePackageUserCommandService(packageRepository);
        };
    }
}
