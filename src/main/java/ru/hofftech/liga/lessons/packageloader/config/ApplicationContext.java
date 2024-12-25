package ru.hofftech.liga.lessons.packageloader.config;

import lombok.Getter;
import ru.hofftech.liga.lessons.packageloader.controller.ConsoleController;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.factory.LogisticServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.factory.UserCommandServiceFactory;

import java.util.Scanner;

@Getter
public class ApplicationContext {
    private final ConsoleController consoleController;

    public ApplicationContext() {
        var fileLoaderService = new FileLoaderService();
        var reportService = new ReportService();

        var truckServiceFactory = new TruckServiceFactory();
        var logisticServiceFactory = new LogisticServiceFactory(truckServiceFactory);

        var userConsoleService = new UserConsoleService(new Scanner(System.in));
        var userCommandServiceFactory = new UserCommandServiceFactory(userConsoleService, fileLoaderService, reportService, logisticServiceFactory, truckServiceFactory);

        var userCommandProcessorService = new UserCommandProcessorService(userConsoleService, userCommandServiceFactory);

        consoleController = new ConsoleController(userCommandProcessorService);
    }
}
