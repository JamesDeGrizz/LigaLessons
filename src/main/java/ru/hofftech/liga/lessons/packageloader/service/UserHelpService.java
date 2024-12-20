package ru.hofftech.liga.lessons.packageloader.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserHelpService {
    public void printHelp(String pattern) {
        log.info("""
                Доступные команды:
                help - эта справка;
                import <filename> - загрузка файла с посылками. Паттерн: {};
                exit - завершение работы;
                """, pattern);
    }
}
