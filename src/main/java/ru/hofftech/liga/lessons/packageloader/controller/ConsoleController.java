package ru.hofftech.liga.lessons.packageloader.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {
    private final UserCommandProcessorService userCommandProcessorService;

    public void listen() {
        userCommandProcessorService.getAndProcessUserCommand();
    }
}
