package ru.hofftech.liga.lessons.packageloader.controller;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.TelegramService;

@Slf4j
public class TelegramController {
    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
        this.telegramService.initialize();
    }
}
