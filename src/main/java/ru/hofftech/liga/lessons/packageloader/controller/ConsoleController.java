package ru.hofftech.liga.lessons.packageloader.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.PackageService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandService;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {
    private final PackageService packageService;
    private final UserCommandService userCommandService;

    public void listen() {
        log.info("Для ознакомления с функционалом введите команду help");

        while (true) {
            var command = userCommandService.getUserCommand();

            switch (command) {
                case Exit:
                    return;
                case Retry:
                    continue;
                case Proceed:
                    packageService.placePackagesFromFileIntoTrucks(userCommandService.getFileName(), userCommandService.getAlgorithm());
            }
        }
    }
}
