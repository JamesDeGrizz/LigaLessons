package ru.hofftech.liga.lessons.packageloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Сервис для взаимодействия с Telegram ботом.
 * Этот класс расширяет {@link TelegramLongPollingBot} и предоставляет методы для обработки сообщений и команд,
 * поступающих от пользователей через Telegram.
 */
@Slf4j
@RequiredArgsConstructor
public class TelegramService extends TelegramLongPollingBot {
    private final String username;
    private final String token;

    private final UserCommandProcessorService userCommandProcessorService;

    /**
     * Инициализирует Telegram бота и регистрирует его в Telegram API.
     */
    public void initialize() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (Exception e){
            log.error("Не получилось установить соединение с телеграм ботом. Этот способ ввода недоступен для данной сессии.");
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    /**
     * Обрабатывает полученные обновления от Telegram.
     *
     * @param update обновление от Telegram
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            log.debug("Получено сообщение {}", messageText);
            var chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    startCommandReceived(update.getMessage().getChat().getFirstName(), chatId);
                    break;
                default:
                    var output = userCommandProcessorService.processRawInput(update.getMessage().getText());
                    sendMessage(output, chatId);
            }
        }
    }

    /**
     * Отправляет сообщение в чат Telegram.
     *
     * @param textToSend текст сообщения для отправки
     */
    private void sendMessage(String textToSend, long chatId) {
        var message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Не получилось отправить сообщение в чат телеграм.");
        }
    }

    private void startCommandReceived(String name, long chatId) {
        sendMessage("Добрый день, " + name + ". Для ознакомления с функционалом введите команду help", chatId);
    }
}
