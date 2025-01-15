package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.UserCommand;
import ru.hofftech.liga.lessons.packageloader.model.enums.CommandSource;
import ru.hofftech.liga.lessons.packageloader.service.factory.UserCommandServiceFactory;

import java.util.concurrent.BlockingQueue;

/**
 * Сервис для обработки команд пользователя.
 * Этот класс предоставляет методы для получения и обработки команд пользователя, поступающих из очереди.
 */
@Slf4j
@AllArgsConstructor
public class UserCommandProcessorService {
    private final UserCommandParserService userCommandParserService;
    private final UserCommandServiceFactory userCommandServiceFactory;
    private final TelegramService telegramService;

    /**
     * Очередь команд пользователя для обработки.
     */
    private final BlockingQueue<UserCommand> queue;

    /**
     * Получает и обрабатывает команды пользователя из очереди.
     * Метод бесконечно ожидает команды от пользователя и выполняет их, отправляя результаты в соответствующий источник (консоль или Telegram).
     */
    public void getAndProcessUserCommand() {
        log.info("Для ознакомления с функционалом введите команду help");

        while (true) {
            UserCommand input = null;
            try {
                input = queue.take();
            } catch (Exception e) {
                continue;
            }

            var command = userCommandParserService.parseCommandAndArguments(input.command());

            var service = userCommandServiceFactory.getUserCommandService(command.command());
            var executionLog = service.execute(command.arguments());

            if (input.commandSource() == CommandSource.Console) {
                log.info(executionLog);
            }
            else {
                telegramService.sendMessage(executionLog);
            }
        }
    }
}
