package ru.hofftech.liga.lessons.packageloader.config;

import lombok.Getter;
import ru.hofftech.liga.lessons.packageloader.controller.ConsoleController;
import ru.hofftech.liga.lessons.packageloader.controller.TelegramController;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportService;
import ru.hofftech.liga.lessons.packageloader.service.TelegramService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandParserService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;
import ru.hofftech.liga.lessons.packageloader.service.UserConsoleService;
import ru.hofftech.liga.lessons.packageloader.service.factory.LogisticServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.factory.UserCommandServiceFactory;

import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Контекст приложения, который инициализирует и управляет основными компонентами системы.
 * Этот класс используется для создания и настройки контроллеров, сервисов и других компонентов,
 * необходимых для работы с посылками.
 */
@Getter
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
     * Имя пользователя бота Telegram.
     */
    private static final String BOT_USERNAME = "LigaLessonsKai_bot";

    /**
     * Токен бота Telegram.
     */
    private static final String BOT_TOKEN = "7209515763:AAHdqNslgwO38YfauIFJ-MvDQPUVO3wadog";

    /**
     * Конструктор, инициализирующий контекст приложения.
     * Создает и настраивает все необходимые компоненты для работы с посылками.
     */
    public ApplicationContext() {
        var queue = new LinkedBlockingQueue();

        var fileLoaderService = new FileLoaderService();
        var reportService = new ReportService();
        var userCommandParserService = new UserCommandParserService();

        var packageRepository = new PackageRepository();

        var truckServiceFactory = new TruckServiceFactory();
        var logisticServiceFactory = new LogisticServiceFactory(truckServiceFactory);

        var userConsoleService = new UserConsoleService(new Scanner(System.in), queue);
        var userCommandServiceFactory = new UserCommandServiceFactory(fileLoaderService, reportService, logisticServiceFactory, truckServiceFactory, packageRepository);

        var telegramService = new TelegramService(queue, BOT_USERNAME, BOT_TOKEN);

        userCommandProcessorService = new UserCommandProcessorService(userCommandParserService, userCommandServiceFactory, telegramService, queue);

        consoleController = new ConsoleController(userConsoleService);
        telegramController = new TelegramController(telegramService);
    }
}
