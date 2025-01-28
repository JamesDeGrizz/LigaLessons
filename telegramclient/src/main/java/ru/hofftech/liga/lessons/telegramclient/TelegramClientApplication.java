package ru.hofftech.liga.lessons.telegramclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TelegramClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramClientApplication.class, args);
    }
}