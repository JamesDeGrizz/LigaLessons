package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class UserCommandProcessorService {
    private final PackageService packageService;
    private final UserHelpService userHelpService;
    private final UserCommandService userCommandService;

    public void getAndProcessUserCommand() {
        log.info("Для ознакомления с функционалом введите команду help");

        while (true) {
            var command = userCommandService.getUserCommand();

            switch (command) {
                case Exit:
                    return;
                case Retry:
                    continue;
                case Help:
                    userHelpService.printHelp(userCommandService.getPattern());
                    continue;
                case Proceed:
                    packageService.placePackagesFromFileIntoTrucks(userCommandService.getFileName(), userCommandService.getAlgorithm());
            }
        }
    }
}
