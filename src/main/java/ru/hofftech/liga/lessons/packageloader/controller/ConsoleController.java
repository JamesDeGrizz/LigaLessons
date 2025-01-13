package ru.hofftech.liga.lessons.packageloader.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {
    private final UserConsoleService userConsoleService;

    public void listen() {
        userConsoleService.start();
    }
}
