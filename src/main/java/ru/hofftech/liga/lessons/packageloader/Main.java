package ru.hofftech.liga.lessons.packageloader;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.controller.ConsoleController;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.PackageService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandService;

import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) {

        log.info("Приложение запущено");

        (new ConsoleController(
                new PackageService(
                        new FileLoaderService(),
                        new ReportService()
                ),
                new UserCommandService(
                        new Scanner(System.in)
                )
        )).listen();

        log.info("Завершение работы");
    }
}