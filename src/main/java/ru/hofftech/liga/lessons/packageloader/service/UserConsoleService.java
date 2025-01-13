package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.UserCommand;
import ru.hofftech.liga.lessons.packageloader.model.enums.CommandSource;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

@Slf4j
@AllArgsConstructor
public class UserConsoleService extends Thread {
    private final Scanner scanner;
    private final BlockingQueue<UserCommand> queue;

    public void run() {
        while (true) {
            log.info("Введите команду:");
            try {
                queue.put(new UserCommand(CommandSource.Console, scanner.nextLine()));
            } catch (InterruptedException e) {
                log.error("Не получилось обработать вашу команду, попробуйте ещё раз");
            }
        }
    }
}
