package ru.hofftech.liga.lessons.packageloader;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.config.ApplicationContext;

@Slf4j
public class Main {
    public static void main(String[] args) {

        log.info("Приложение запущено");

        ApplicationContext context = new ApplicationContext();
        context.getConsoleController().listen();

        log.info("Завершение работы");
    }


}