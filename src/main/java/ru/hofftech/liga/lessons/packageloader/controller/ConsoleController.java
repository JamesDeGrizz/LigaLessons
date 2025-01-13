package ru.hofftech.liga.lessons.packageloader.controller;

import lombok.RequiredArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;

/**
 * Контроллер консоли, который управляет взаимодействием с пользователем через консоль.
 * Этот класс используется для запуска и управления сервисом консоли пользователя.
 */
@RequiredArgsConstructor
public class ConsoleController {
    /**
     * Сервис консоли пользователя, который обрабатывает ввод и вывод данных в консоли.
     */
    private final UserConsoleService userConsoleService;

    /**
     * Запускает прослушивание ввода пользователя в консоли.
     * Этот метод инициализирует сервис консоли пользователя и начинает обработку команд, связанных с посылками.
     */
    public void listen() {
        userConsoleService.start();
    }
}
