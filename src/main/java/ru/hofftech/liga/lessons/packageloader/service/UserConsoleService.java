package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.UserCommand;
import ru.hofftech.liga.lessons.packageloader.model.enums.CommandSource;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * Сервис для взаимодействия с пользователем через консоль.
 * Этот класс расширяет {@link Thread} и предоставляет методы для обработки команд пользователя, введенных через консоль.
 */
@Slf4j
@AllArgsConstructor
public class UserConsoleService extends Thread {
    /**
     * Сканер для чтения ввода пользователя из консоли.
     */
    private final Scanner scanner;

    /**
     * Очередь команд пользователя для обработки.
     */
    private final BlockingQueue<UserCommand> queue;

    /**
     * Запускает поток для обработки команд пользователя, введенных через консоль.
     * Метод бесконечно ожидает ввода команд от пользователя и добавляет их в очередь для дальнейшей обработки.
     */
    public void run() {
        while (true) {
            log.info("Введите команду:");
            try {
                queue.put(new UserCommand(CommandSource.CONSOLE, scanner.nextLine()));
            } catch (InterruptedException e) {
                log.error("Не получилось обработать вашу команду, попробуйте ещё раз");
            }
        }
    }
}
