package ru.hofftech.liga.lessons.packageloader;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.controller.ConsoleController;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.PackageService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.UserHelpService;
import ru.hofftech.liga.lessons.packageloader.service.factory.LogisticServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.factory.UserCommandServiceFactory;

import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) {

        log.info("Приложение запущено");

        var controller = getConsoleController();
        controller.listen();

        log.info("Завершение работы");
    }

    private static ConsoleController getConsoleController() {
        var fileLoaderService = new FileLoaderService();
        var reportService = new ReportService();

        var truckServiceFactory = new TruckServiceFactory();
        var logisticServiceFactory = new LogisticServiceFactory(truckServiceFactory);

        var packageService = new PackageService(fileLoaderService, reportService, logisticServiceFactory);
        var userHelpService = new UserHelpService();
        var userConsoleService = new UserConsoleService(new Scanner(System.in));
        var userCommandServiceFactory = new UserCommandServiceFactory(packageService, userHelpService, userConsoleService);

        var userCommandProcessorService = new UserCommandProcessorService(userConsoleService, userCommandServiceFactory);

        var controller = new ConsoleController(userCommandProcessorService);
        return controller;
    }
}