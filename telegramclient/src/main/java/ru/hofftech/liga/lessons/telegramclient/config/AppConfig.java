package ru.hofftech.liga.lessons.telegramclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.hofftech.liga.lessons.telegramclient.controller.TelegramController;
import ru.hofftech.liga.lessons.telegramclient.service.ParcelLoaderClient;
import ru.hofftech.liga.lessons.telegramclient.service.TelegramService;
import ru.hofftech.liga.lessons.telegramclient.service.UserCommandParserService;
import ru.hofftech.liga.lessons.telegramclient.service.UserCommandProcessorService;

@Slf4j
@Configuration
public class AppConfig {
    @Value("${telegram.credentials.username}")
    private String botUsername;

    @Value("${telegram.credentials.token}")
    private String botToken;

    @Value("${services.url.parcels-loader}")
    private String parcelsLoaderUrl;


    // Telegram
    @Bean
    public TelegramController telegramController(TelegramService telegramService) {
        return new TelegramController(telegramService);
    }

    @Bean
    public TelegramService telegramService(UserCommandProcessorService userCommandProcessorService) {
        try {
            return new TelegramService(botUsername, botToken, userCommandProcessorService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


    // Services
    @Bean
    public UserCommandParserService userCommandParserService() {
        return new UserCommandParserService();
    }

    @Bean
    public ParcelLoaderClient parcelLoaderClientService() {
        var client = RestClient.builder()
                .baseUrl(parcelsLoaderUrl)
                .build();

        var adapter = RestClientAdapter.create(client);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return httpServiceProxyFactory.createClient(ParcelLoaderClient.class);
    }
}
