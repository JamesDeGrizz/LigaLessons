package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.UserCommand;
import ru.hofftech.liga.lessons.packageloader.model.enums.CommandSource;
import ru.hofftech.liga.lessons.packageloader.service.factory.UserCommandServiceFactory;

import java.util.concurrent.BlockingQueue;

@Slf4j
@AllArgsConstructor
public class UserCommandProcessorService {
    private final UserCommandParserService userCommandParserService;
    private final UserCommandServiceFactory userCommandServiceFactory;
    private final TelegramService telegramService;

    private final BlockingQueue<UserCommand> queue;

    public void getAndProcessUserCommand() {
        log.info("Для ознакомления с функционалом введите команду help");

        while (true) {
            UserCommand input = null;
            try {
                input = queue.take();
            } catch (Exception e) {
                continue;
            }

            var command = userCommandParserService.parseCommand(input.command());
            var commandArguments = userCommandParserService.parseArguments(input.command());

            var service = userCommandServiceFactory.getUserCommandService(command);
            var executionLog = service.execute(commandArguments);

            if (input.commandSource() == CommandSource.Console) {
                log.info(executionLog);
            }
            else {
                telegramService.sendMessage(executionLog);
            }
        }
    }
}
