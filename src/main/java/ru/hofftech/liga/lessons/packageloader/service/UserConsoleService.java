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
    private static final Pattern PACKAGES_PATTERN = Pattern.compile("import (.+\\.txt)");
    private static final Pattern TRUCKS_PATTERN = Pattern.compile("import (.+\\.json)");
    private static final int FILENAME_ORDINAL_NUMBER = 1;
    private static final String EXIT_COMMAND = "exit";
    private static final String HELP_COMMAND = "help";
    private final Scanner scanner;

    @Getter
    private String fileName;

    public UserConsoleService(Scanner scanner) {
        this.scanner = scanner;
    }

    public Command getUserCommand() {
        log.info("Введите команду:");
        var command = scanner.nextLine();

        if (checkCommandNullOrEmpty(command)) {
            return Command.Retry;
        }

        if (command.equals(EXIT_COMMAND)) {
            return Command.Exit;
        }

        if (command.equals(HELP_COMMAND)) {
            return Command.Help;
        }

        return parseRestCommand(command);
    }

    public PlacingAlgorithm getAlgorithm() {
        var canStop = false;
        var algorithm = 0;

        while (!canStop) {
            log.info("""
                        Выбор алгоритма погрузки:
                        0 - один грузовик = одна посылка
                        1 - один грузовик = максимум посылок
                        2 - равномерная погрузка по машинам
                    """);

            String userCommand = null;
            try {
                userCommand = scanner.nextLine();

                if (checkCommandNullOrEmpty(userCommand)) {
                    continue;
                }

                algorithm = Integer.parseInt(userCommand);

                if (algorithm < 0 || algorithm > 2) {
                    log.error("Неправильное значение типа алгоритма: {}", userCommand);
                    continue;
                }
            } catch (Exception e) {
                log.error("Введённое значение нельзя привести к числу: {}", userCommand == null ? "" : userCommand);
                continue;
            }

            canStop = true;
        }

        return PlacingAlgorithm.valueOf(algorithm);
    }

    public int getTrucksCount() {
        var canStop = false;
        var trucksCount = 0;

        while (!canStop) {
            log.info("""
                        Введите максимальное количество грузовиков:
                    """);

            var truckCountString = scanner.nextLine();

            if (checkCommandNullOrEmpty(truckCountString)) {
                continue;
            }

            try {
                trucksCount = Integer.parseInt(truckCountString);

                if (trucksCount < 0) {
                    log.error("Количество машин не может быть меньше 0. Попробуйте ещё раз.");
                    continue;
                }
            } catch (NumberFormatException e) {
                log.error("Вы ввели не число или число больше {}. Попробуйте ещё раз.", Integer.MAX_VALUE);
                continue;
            }

            canStop = true;
        }

        return trucksCount;
    }

    public boolean needSaveToFile() {
        var canStop = false;
        String command = null;

        while (!canStop) {
            log.info("""
                        Вы хотите сохранить результат работы в файл? Введите + или -
                    """);
            command = scanner.nextLine();

            if (checkCommandNullOrEmpty(command)) {
                continue;
            }

            if (!command.equals("+") && !command.equals("-")) {
                log.error("Вы ввели неправильную команду. Попробуйте ещё раз");
                continue;
            }

            canStop = true;
        }

        return command.equals("+");
    }

    public String getReportFileName() {
        var canStop = false;
        String fileName = null;

        while (!canStop) {
            try {
                log.info("""
                            Введите название файла. Можно указать абсолютный путь, в противном случае файл будет сохранён в директорию приложения.
                            Лучше указывайте название файла с расширением.
                        """);
                fileName = scanner.nextLine();

                if (checkCommandNullOrEmpty(fileName)) {
                    continue;
                }

                Paths.get(fileName);

                canStop = true;
            } catch (Exception e) {
                log.error("Вы ввели некорректное название. Попробуйте ещё раз.");
            }
        }

        return fileName;
    }

    private boolean checkCommandNullOrEmpty(String command) {
        if (command == null || command.isEmpty()) {
            log.error("Вы ничего не ввели. Попробуйте ещё раз.");
            return true;
        }
        return false;
    }

    private Command parseRestCommand(String command) {
        var packagesMatcher = PACKAGES_PATTERN.matcher(command);
        var trucksMatcher = TRUCKS_PATTERN.matcher(command);

        var packagesMatches = packagesMatcher.matches();
        var trucksMatches = trucksMatcher.matches();
        if (!packagesMatches && !trucksMatches) {
            return Command.Retry;
        }

        fileName = packagesMatches ? packagesMatcher.group(FILENAME_ORDINAL_NUMBER) : trucksMatcher.group(FILENAME_ORDINAL_NUMBER);
        return packagesMatches ? Command.ProceedPackages : Command.ProceedTrucks;
    }
}
