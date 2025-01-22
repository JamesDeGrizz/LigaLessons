package ru.hofftech.liga.lessons.packageloader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PackageLoaderApplication {
    public static void main(String[] args) {
        log.info("Приложение запущено");

        SpringApplication.run(PackageLoaderApplication.class, args);

        log.info("Завершение работы");
    }
}