package ru.hofftech.liga.lessons.packageloader.service.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportPackageService;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadPackagesUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.UnloadTrucksUserCommandService;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class UserCommandServiceFactoryTest {
    UserCommandServiceFactory userCommandServiceFactory;

    @BeforeEach
    void setUpOnce() {
        userCommandServiceFactory = new UserCommandServiceFactory(
                new UserConsoleService(new Scanner(System.in)),
                new FileLoaderService(),
                new ReportPackageService(),
                new LogisticServiceFactory(new TruckServiceFactory()),
                new TruckServiceFactory());
    }

    @Test
    void getUserCommandService_givenExitCommand_returnsExitUserCommandService() {
        var userCommandService = userCommandServiceFactory.getUserCommandService(Command.EXIT);
        assertThat(userCommandService)
                .isInstanceOf(ExitUserCommandService.class);
    }

    @Test
    void getUserCommandService_givenHelpCommand_returnsHelpUserCommandService() {
        var userCommandService = userCommandServiceFactory.getUserCommandService(Command.HELP);
        assertThat(userCommandService)
                .isInstanceOf(HelpUserCommandService.class);
    }

    @Test
    void getUserCommandService_givenRetryCommand_returnsRetryUserCommandService() {
        var userCommandService = userCommandServiceFactory.getUserCommandService(Command.RETRY);
        assertThat(userCommandService)
                .isInstanceOf(RetryUserCommandService.class);
    }

    @Test
    void getUserCommandService_givenProceedPackagesCommand_returnsProceedPackagesUserCommandService() {
        var userCommandService = userCommandServiceFactory.getUserCommandService(Command.LOAD_PACKAGES);
        assertThat(userCommandService)
                .isInstanceOf(LoadPackagesUserCommandService.class);
    }

    @Test
    void getUserCommandService_givenProceedTrucksCommand_returnsProceedTrucksUserCommandService() {
        var userCommandService = userCommandServiceFactory.getUserCommandService(Command.Unload);
        assertThat(userCommandService)
                .isInstanceOf(UnloadTrucksUserCommandService.class);
    }
}