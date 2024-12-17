package ru.hofftech.liga.lessons;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.controller.ConsoleController;
import ru.hofftech.liga.lessons.repository.FileRepository;
import ru.hofftech.liga.lessons.service.PackageService;

@Slf4j
public class Main {
    public static void main(String[] args) {

        log.info("Приложение запущено");

        (new ConsoleController(new PackageService(new FileRepository()))).listen();

        log.info("Завершение работы");
    }
}