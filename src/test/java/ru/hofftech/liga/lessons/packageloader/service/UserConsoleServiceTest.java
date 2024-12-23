package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class UserConsoleServiceTest {
    ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUpOnce() {
        outputStream = new ByteArrayOutputStream();
        var printStream = new PrintStream(outputStream);
        System.setOut(printStream);
    }

    @Test
    void getUserCommand_givenExitString_returnsExitCommand() {
        var userCommand = """
                exit
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var command = userConsoleService.getUserCommand();

        assertThat(command)
                .isEqualTo(Command.Exit);

        assertThat(outputStream.toString())
                .contains("Введите команду");
    }

    @Test
    void getUserCommand_givenWrongString_returnsRetryCommand() {
        var userCommand = """
                wrong command
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var command = userConsoleService.getUserCommand();

        assertThat(command)
                .isEqualTo(Command.Retry);

        assertThat(outputStream.toString())
                .contains("Введите команду")
                .contains("Неправильная команда. Попробуйте ещё раз.");
    }

    @Test
    void getUserCommand_givenImportTxtString_returnsProceedPackagesCommand() {
        var userCommand = """
                import somefile.txt
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var command = userConsoleService.getUserCommand();

        assertThat(command)
                .isEqualTo(Command.ProceedPackages);

        assertThat(outputStream.toString())
                .contains("Введите команду");
    }

    @Test
    void getUserCommand_givenImportJsonString_returnsProceedTrucksCommand() {
        var userCommand = """
                import somefile.json
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var command = userConsoleService.getUserCommand();

        assertThat(command)
                .isEqualTo(Command.ProceedTrucks);

        assertThat(outputStream.toString())
                .contains("Введите команду");
    }

    @Test
    void getUserCommand_givenHelpString_returnsHelpCommand() {
        var userCommand = """
                help
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var command = userConsoleService.getUserCommand();

        assertThat(command)
                .isEqualTo(Command.Help);

        assertThat(outputStream.toString())
                .contains("Введите команду");
    }

    @Test
    void getAlgorithm_givenZero_returnsOnePerTruck() {
        var userCommand = """
                0
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var algorithm = userConsoleService.getAlgorithm();

        assertThat(algorithm)
                .isEqualTo(PlacingAlgorithm.OnePerTruck);

        assertThat(outputStream.toString())
                .contains("""
                    Выбор алгоритма погрузки:
                    0 - один грузовик = одна посылка
                    1 - один грузовик = максимум посылок
                    2 - равномерная погрузка по машинам
                """);
    }

    @Test
    void getAlgorithm_givenOne_returnsFillTruck() {
        var userCommand = """
                1
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var algorithm = userConsoleService.getAlgorithm();

        assertThat(algorithm)
                .isEqualTo(PlacingAlgorithm.FillTruck);

        assertThat(outputStream.toString())
                .contains("""
                    Выбор алгоритма погрузки:
                    0 - один грузовик = одна посылка
                    1 - один грузовик = максимум посылок
                    2 - равномерная погрузка по машинам
                """);
    }

    @Test
    void getAlgorithm_givenTwo_returnsBalanced() {
        var userCommand = """
                2
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var algorithm = userConsoleService.getAlgorithm();

        assertThat(algorithm)
                .isEqualTo(PlacingAlgorithm.Balanced);

        assertThat(outputStream.toString())
                .contains("""
                    Выбор алгоритма погрузки:
                    0 - один грузовик = одна посылка
                    1 - один грузовик = максимум посылок
                    2 - равномерная погрузка по машинам
                """);
    }

    @Test
    void getAlgorithm_givenNan_returnsAnyButLogError() {
        var userCommand = """
                qwe
                2
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        userConsoleService.getAlgorithm();

        assertThat(outputStream.toString())
                .contains("Неправильное значение типа алгоритма:")
                .contains("""
                    Выбор алгоритма погрузки:
                    0 - один грузовик = одна посылка
                    1 - один грузовик = максимум посылок
                    2 - равномерная погрузка по машинам
                """);
    }

    @Test
    void getAlgorithm_givenLessThanZero_returnsAnyButLogError() {
        var userCommand = """
                -123
                2
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        userConsoleService.getAlgorithm();

        assertThat(outputStream.toString())
                .contains("Неправильное значение типа алгоритма:")
                .contains("""
                    Выбор алгоритма погрузки:
                    0 - один грузовик = одна посылка
                    1 - один грузовик = максимум посылок
                    2 - равномерная погрузка по машинам
                """);
    }

    @Test
    void getAlgorithm_givenMoreThanTwo_returnsAnyButLogError() {
        var userCommand = """
                123
                2
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        userConsoleService.getAlgorithm();

        assertThat(outputStream.toString())
                .contains("Неправильное значение типа алгоритма:")
                .contains("""
                    Выбор алгоритма погрузки:
                    0 - один грузовик = одна посылка
                    1 - один грузовик = максимум посылок
                    2 - равномерная погрузка по машинам
                """);
    }

    @Test
    void getTrucksCount_givenFive_returnsFive() {
        var userCommand = """
                5
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var trucksCount = userConsoleService.getTrucksCount();

        assertThat(trucksCount)
                .isEqualTo(5);

        assertThat(outputStream.toString())
                .contains("Введите максимальное количество грузовиков:");
    }

    @Test
    void getTrucksCount_givenNan_returnsAnyButLogError() {
        var userCommand = """
                qwe
                5
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        userConsoleService.getTrucksCount();

        assertThat(outputStream.toString())
                .contains("Введите максимальное количество грузовиков:")
                .contains("Вы ввели не число или число больше 2147483647. Попробуйте ещё раз");
    }

    @Test
    void getTrucksCount_givenMoreThanIntMaxValue_returnsAnyButLogError() {
        var userCommand = """
                3147483647
                5
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        userConsoleService.getTrucksCount();

        assertThat(outputStream.toString())
                .contains("Введите максимальное количество грузовиков:")
                .contains("Вы ввели не число или число больше 2147483647. Попробуйте ещё раз");
    }

    @Test
    void getTrucksCount_givenLessThanZero_returnsAnyButLogError() {
        var userCommand = """
                -3
                5
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        userConsoleService.getTrucksCount();

        assertThat(outputStream.toString())
                .contains("Введите максимальное количество грузовиков:")
                .contains("Количество машин не может быть меньше 0. Попробуйте ещё раз.");
    }

    @Test
    void needSaveToFile_givenPlus_returnsTrue() {
        var userCommand = """
                +
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var save = userConsoleService.needSaveToFile();
        assertThat(save)
                .isTrue();

        assertThat(outputStream.toString())
                .contains("Вы хотите сохранить результат работы в файл? Введите + или -");
    }

    @Test
    void needSaveToFile_givenMinus_returnsFalse() {
        var userCommand = """
                -
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var save = userConsoleService.needSaveToFile();
        assertThat(save)
                .isFalse();

        assertThat(outputStream.toString())
                .contains("Вы хотите сохранить результат работы в файл? Введите + или -");
    }

    @Test
    void needSaveToFile_givenWrongInput_returnsAnyButLogError() {
        var userCommand = """
                qwe
                -
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        userConsoleService.needSaveToFile();

        assertThat(outputStream.toString())
                .contains("Вы хотите сохранить результат работы в файл? Введите + или -")
                .contains("Вы ввели неправильную команду. Попробуйте ещё раз");
    }

    @Test
    void getFileName_givenCorrectInput_returnsFilename() {
        var userCommand = "filename";
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        var filename = userConsoleService.getReportFileName();

        assertThat(filename)
                .isEqualTo(userCommand);

        assertThat(outputStream.toString())
                .contains("Введите название файла. Можно указать абсолютный путь, в противном случае файл будет сохранён в директорию приложения.");
    }

    @Test
    void getFileName_givenSpecialCharacters_returnsAnyButLogError() {
        var userCommand = """
                R:/`"!@#Users
                filename
                """;
        var inputStream = new ByteArrayInputStream(userCommand.getBytes());
        var scanner = new Scanner(inputStream);
        var userConsoleService = new UserConsoleService(scanner);

        userConsoleService.getReportFileName();

        assertThat(outputStream.toString())
                .contains("Введите название файла. Можно указать абсолютный путь, в противном случае файл будет сохранён в директорию приложения.")
                .contains("Вы ввели некорректное название. Попробуйте ещё раз.");
    }
}