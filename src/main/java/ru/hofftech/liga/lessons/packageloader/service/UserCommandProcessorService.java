package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.factory.UserCommandServiceFactory;

@Slf4j
@AllArgsConstructor
public class UserCommandProcessorService {
    private final UserConsoleService userConsoleService;
    private final UserCommandServiceFactory userCommandServiceFactory;

    public void getAndProcessUserCommand() {
        log.info("Для ознакомления с функционалом введите команду help");

        while (true) {
            var command = userConsoleService.getUserCommand();
            userCommandServiceFactory.getUserCommandService(command).execute();
        }
    }
}
