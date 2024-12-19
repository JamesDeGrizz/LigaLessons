package ru.hofftech.liga.lessons.packageloader.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;

import java.util.Scanner;
import java.util.regex.Pattern;

@Slf4j
public class UserCommandService {
    private final String pattern = "import (.+\\.txt)";
    private final Pattern IMPORT_COMMAND_PATTERN = Pattern.compile(pattern);

    @Getter
    private String fileName;

    private Scanner scanner;

    public UserCommandService(Scanner scanner) {
        this.scanner = scanner;
    }

    public Command getUserCommand() {
        log.info("Введите команду:");
        scanner.hasNextLine();
        var command = scanner.nextLine();

        if (command.equals("exit")) {
            return Command.Exit;
        }

        if (command.equals("help")) {
            log.info("""
                Доступные команды:
                help - эта справка;
                import <filename> - загрузка файла с посылками. Паттерн: {};
                exit - завершение работы;
                """, pattern);

            return Command.Retry;
        }

        var matcher = IMPORT_COMMAND_PATTERN.matcher(command);

        if (!matcher.matches()) {
            log.error("Неправильная команда. Попробуйте ещё раз.");
            return Command.Retry;
        }

        fileName = matcher.group(1);
        return Command.Proceed;
    }

    public PlacingAlgorithm getAlgorithm() {
        while (true) {
            log.info("""
                    Выбор алгоритма погрузки:
                    0 - один грузовик = одна посылка 
                    1 - один грузовик = максимум посылок
                """);
            scanner.hasNextLine();
            var algorithm = Integer.valueOf(scanner.nextLine());

            if (algorithm == null || algorithm < 0 || algorithm > 1) {
                log.error("Неправильное значение типа алгоритма: {}", algorithm);
                continue;
            }

            return PlacingAlgorithm.values()[algorithm];
        }
    }
}
