package ru.hofftech.liga.lessons.packageloader.controller;

import ru.hofftech.liga.lessons.packageloader.service.TelegramService;

/**
 * Контроллер Telegram, который управляет взаимодействием с пользователем через Telegram.
 * Этот класс используется для инициализации и управления сервисом Telegram, который обрабатывает команды, связанные с посылками.
 */
public class TelegramController {
    /**
     * Сервис Telegram, который обрабатывает взаимодействие с пользователем через Telegram.
     */
    private final TelegramService telegramService;

    /**
     * Конструктор, инициализирующий контроллер Telegram.
     * Создает и инициализирует сервис Telegram для обработки команд, связанных с посылками.
     *
     * @param telegramService сервис Telegram
     */
    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
        this.telegramService.initialize();
    }
}
