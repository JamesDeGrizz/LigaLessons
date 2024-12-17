package ru.hofftech.liga.lessons.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.model.Command;
import ru.hofftech.liga.lessons.model.PlacingAlgorithm;
import ru.hofftech.liga.lessons.service.PackageService;

import java.util.Scanner;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {
    private final String pattern = "import (.+\\.txt)";
    private final Pattern IMPORT_COMMAND_PATTERN = Pattern.compile(pattern);
    private final Scanner scanner = new Scanner(System.in);
    private final PackageService packageService;

    private String fileName;

    // todo: придумать куда перенести логику из контроллера
    public void listen() {
        log.info("Для ознакомления с функционалом введите команду help");

        while (true) {
            log.info("Введите команду:");
            scanner.hasNextLine();
            var commandString = scanner.nextLine();

            var command = processUserCommand(commandString);

            switch (command) {
                case Exit:
                    return;
                case Retry:
                    continue;
                case Proceed:
                    // todo: убрать это в метод или в сервис
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

                    packageService.placePackagesFromFileIntoTrucks(fileName, PlacingAlgorithm.values()[algorithm]);
                    continue;
                default:
                    log.error("Неизвестный сбой");
                    return;
            }
        }
    }

    private Command processUserCommand(String command) {
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
}
