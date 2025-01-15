package ru.hofftech.liga.lessons.packageloader.service.factory;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportPackageService;
import ru.hofftech.liga.lessons.packageloader.service.ReportTruckService;
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
    private final ReportPackageService reportPackageService;
    private final ReportTruckService reportTruckService;
    private final LogisticServiceFactory logisticServiceFactory;
    private final TruckServiceFactory truckServiceFactory;
    private final PackageRepository packageRepository;

    public UserCommandService getUserCommandService(Command command) {
        return switch (command) {
            case EXIT -> new ExitUserCommandService();
            case RETRY -> new RetryUserCommandService();
            case HELP -> new HelpUserCommandService();
            case LOAD_PACKAGES -> new LoadPackagesUserCommandService(fileLoaderService, reportTruckService, logisticServiceFactory, packageRepository);
            case UNLOAD_TRUCKS -> new UnloadTrucksUserCommandService(fileLoaderService, reportPackageService, truckServiceFactory);
            case CREATE_PACKAGE -> new CreatePackageUserCommandService(packageRepository);
            case FIND_PACKAGE -> new FindPackageUserCommandService(packageRepository);
            case EDIT_PACKAGE -> new EditPackageUserCommandService(packageRepository);
            case DELETE_PACKAGE -> new DeletePackageUserCommandService(packageRepository);
        };
    }
}
