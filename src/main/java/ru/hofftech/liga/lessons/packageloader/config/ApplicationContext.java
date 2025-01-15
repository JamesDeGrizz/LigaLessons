package ru.hofftech.liga.lessons.packageloader.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.controller.ConsoleController;
import ru.hofftech.liga.lessons.packageloader.controller.TelegramController;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportPackageService;
import ru.hofftech.liga.lessons.packageloader.service.ReportTruckService;
import ru.hofftech.liga.lessons.packageloader.service.TelegramService;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandParserService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.factory.LogisticServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.factory.UserCommandServiceFactory;
import ru.hofftech.liga.lessons.packageloader.validator.CreatePackageUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.EditPackageUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.LoadPackagesUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.UnloadTrucksUserCommandValidator;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Контекст приложения, который инициализирует и управляет основными компонентами системы.
 * Этот класс используется для создания и настройки контроллеров, сервисов и других компонентов,
 * необходимых для работы с посылками.
 */
@Getter
@Slf4j
public class ApplicationContext {
    /**
     * Контроллер консоли для взаимодействия с пользователем через консоль.
     */
    private final ConsoleController consoleController;

    /**
     * Контроллер Telegram для взаимодействия с пользователем через Telegram.
     */
    private final TelegramController telegramController;

    /**
     * Сервис обработки команд пользователя.
     */
    private final UserCommandProcessorService userCommandProcessorService;

    /**
     * Конструктор, инициализирующий контекст приложения.
     * Создает и настраивает все необходимые компоненты для работы с посылками.
     */
    public ApplicationContext() {
        var botUsername = "";
        var botToken = "";
        try {
            botUsername = System.getenv("bot_username");
            botToken = System.getenv("bot_token");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        var createPackageUserCommandValidator = new CreatePackageUserCommandValidator();
        var editPackageUserCommandValidator = new EditPackageUserCommandValidator();
        var loadPackagesUserCommandValidator = new LoadPackagesUserCommandValidator();
        var unloadTrucksUserCommandValidator = new UnloadTrucksUserCommandValidator();

        var queue = new LinkedBlockingQueue();

        var fileLoaderService = new FileLoaderService();
        var reportPackageService = new ReportPackageService();
        var reportTruckService = new ReportTruckService();
        var userCommandParserService = new UserCommandParserService();

        var packageRepository = new PackageRepository();
        var truckService = new TruckService();

        var logisticServiceFactory = new LogisticServiceFactory(truckService);

        var userConsoleService = new UserConsoleService(new Scanner(System.in), queue);
        var userCommandServiceFactory = new UserCommandServiceFactory(
                fileLoaderService,
                reportPackageService,
                reportTruckService,
                logisticServiceFactory,
                packageRepository,
                createPackageUserCommandValidator,
                editPackageUserCommandValidator,
                loadPackagesUserCommandValidator,
                unloadTrucksUserCommandValidator);

        var telegramService = new TelegramService(queue, botUsername, botToken);

        userCommandProcessorService = new UserCommandProcessorService(userCommandParserService, userCommandServiceFactory, telegramService, queue);

        consoleController = new ConsoleController(userConsoleService);
        telegramController = new TelegramController(telegramService);
    }

    public void run() {
        consoleController.listen();
        userCommandProcessorService.process();
    }
}
