package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

@Slf4j
@AllArgsConstructor
public class HelpUserCommandService implements UserCommandService {
    @Override
    public void execute() {
        log.info("""
                Доступные команды:
                help - эта справка;
                import <filename>.txt - загрузка файла с посылками;
                import <filename>.json - загрузка файла с грузовиками;
                exit - завершение работы;
                """);
    }
}
