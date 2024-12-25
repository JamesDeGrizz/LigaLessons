package ru.hofftech.liga.lessons.packageloader.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

@Slf4j
public class UserConsoleService {
    private final Pattern PACKAGES_PATTERN = Pattern.compile("import (.+\\.txt)");
    private final Pattern TRUCKS_PATTERN = Pattern.compile("import (.+\\.json)");
    private final Scanner scanner;

    @Getter
    private String fileName;

    public UserConsoleService(Scanner scanner) {
        this.scanner = scanner;
    }

    public Command getUserCommand() {
        log.info("Введите команду:");
        String command = null;
        if (scanner.hasNextLine()) {
            command = scanner.nextLine();
        }

        if (command == null || command.isEmpty()) {
            log.error("Вы не ввели команду. Попробуйте ещё раз.");
            return Command.Retry;
        }

        if (command.equals("exit")) {
            return Command.Exit;
        }

        if (command.equals("help")) {
            return Command.Help;
        }

        var packagesMatcher = PACKAGES_PATTERN.matcher(command);
        var trucksMatcher = TRUCKS_PATTERN.matcher(command);

        if (!packagesMatcher.matches() && !trucksMatcher.matches()) {
            log.error("Неправильная команда. Попробуйте ещё раз.");
            return Command.Retry;
        }

        fileName = packagesMatcher.matches() ? packagesMatcher.group(1) : trucksMatcher.group(1);
        return packagesMatcher.matches() ? Command.ProceedPackages : Command.ProceedTrucks;
    }

    public PlacingAlgorithm getAlgorithm() {
        while (true) {
            log.info("""
                        Выбор алгоритма погрузки:
                        0 - один грузовик = одна посылка
                        1 - один грузовик = максимум посылок
                        2 - равномерная погрузка по машинам
                    """);

            int algorithm = 0;
            String userCommand = null;
            try {
                if (scanner.hasNextLine()) {
                    userCommand = scanner.nextLine();
                }

                if (userCommand == null || userCommand.isEmpty()) {
                    logWrongAlgorithmArgumentError(userCommand);
                    continue;
                }

                algorithm = Integer.parseInt(userCommand);
            } catch (Exception e) {
                logWrongAlgorithmArgumentError(userCommand);
                continue;
            }

            if (algorithm < 0 || algorithm > 2) {
                logWrongAlgorithmArgumentError(userCommand);
                continue;
            }

            return PlacingAlgorithm.values()[algorithm];
        }
    }

    public int getTrucksCount() {
        while (true) {
            log.info("""
                        Введите максимальное количество грузовиков:
                    """);
            if (!scanner.hasNextLine()) {
                log.error("Ошибка чтения консоли. Попробуйте ещё раз.");
                continue;
            }

            int trucksCount = 0;
            try {
                trucksCount = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                log.error("Вы ввели не число или число больше {}. Попробуйте ещё раз.", Integer.MAX_VALUE);
                continue;
            }
            if (trucksCount < 0) {
                log.error("Количество машин не может быть меньше 0. Попробуйте ещё раз.");
                continue;
            }

            return trucksCount;
        }
    }

    public boolean needSaveToFile() {
        while (true) {
            log.info("""
                        Вы хотите сохранить результат работы в файл? Введите + или -
                    """);
            if (!scanner.hasNextLine()) {
                log.error("Ошибка чтения консоли. Попробуйте ещё раз.");
                continue;
            }

            var command = scanner.nextLine();
            if (!command.equals("+") && !command.equals("-")) {
                log.error("Вы ввели неправильную команду. Попробуйте ещё раз");
                continue;
            }

            return command.equals("+");
        }
    }

    public String getReportFileName() {
        while (true) {
            try {
                log.info("""
                            Введите название файла. Можно указать абсолютный путь, в противном случае файл будет сохранён в директорию приложения.
                            Лучше указывайте название файла с расширением.
                        """);
                if (!scanner.hasNextLine()) {
                    log.error("Ошибка чтения консоли. Попробуйте ещё раз.");
                    continue;
                }

                var fileName = scanner.nextLine();

                Paths.get(fileName);

                return fileName;

            } catch (Exception e) {
                log.error("Вы ввели некорректное название. Попробуйте ещё раз.");
            }
        }
    }

    private static void logWrongAlgorithmArgumentError(String userText) {
        log.error("Неправильное значение типа алгоритма: {}", userText == null ? "" : userText);
    }
}
