package ru.hofftech.liga.lessons.telegramclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.liga.lessons.telegramclient.controller.TelegramController;
import ru.hofftech.liga.lessons.telegramclient.service.FileLoaderService;
import ru.hofftech.liga.lessons.telegramclient.service.ReportParcelService;
import ru.hofftech.liga.lessons.telegramclient.service.ReportTruckService;
import ru.hofftech.liga.lessons.telegramclient.service.TelegramService;
import ru.hofftech.liga.lessons.telegramclient.service.TruckService;
import ru.hofftech.liga.lessons.telegramclient.service.UserCommandParserService;
import ru.hofftech.liga.lessons.telegramclient.service.UserCommandProcessorService;
import ru.hofftech.liga.lessons.telegramclient.service.logistic.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.telegramclient.service.logistic.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.telegramclient.service.logistic.OnePerTruckLogisticService;
import ru.hofftech.liga.lessons.telegramclient.validator.CreateParcelUserCommandValidator;
import ru.hofftech.liga.lessons.telegramclient.validator.DeleteParcelUserCommandValidator;
import ru.hofftech.liga.lessons.telegramclient.validator.EditParcelUserCommandValidator;
import ru.hofftech.liga.lessons.telegramclient.validator.FindUserOrdersUserCommandValidator;
import ru.hofftech.liga.lessons.telegramclient.validator.LoadParcelsUserCommandValidator;
import ru.hofftech.liga.lessons.telegramclient.validator.UnloadTrucksUserCommandValidator;

@Slf4j
@Configuration
@EnableConfigurationProperties(BillingConfiguration.class)
public class AppConfig {
    @Value("${telegram.credentials.username}")
    private String botUsername;

    @Value("${telegram.credentials.token}")
    private String botToken;


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


    // Validators
    @Bean
    public CreateParcelUserCommandValidator createParcelUserCommandValidator() {
        return new CreateParcelUserCommandValidator();
    }

    @Bean
    public EditParcelUserCommandValidator editParcelUserCommandValidator() {
        return new EditParcelUserCommandValidator();
    }

    @Bean
    public LoadParcelsUserCommandValidator loadParcelsUserCommandValidator() {
        return new LoadParcelsUserCommandValidator();
    }

    @Bean
    public UnloadTrucksUserCommandValidator unloadTrucksUserCommandValidator() {
        return new UnloadTrucksUserCommandValidator();
    }

    @Bean
    public DeleteParcelUserCommandValidator deleteParcelUserCommandValidator() {
        return new DeleteParcelUserCommandValidator();
    }

    @Bean
    public FindUserOrdersUserCommandValidator findUserOrdersUserCommandValidator() {
        return new FindUserOrdersUserCommandValidator();
    }


    // Services
    @Bean
    public FileLoaderService fileLoaderService() {
        return new FileLoaderService();
    }

    @Bean
    public ReportParcelService reportParcelService() {
        return new ReportParcelService();
    }

    @Bean
    public ReportTruckService reportTruckService() {
        return new ReportTruckService();
    }

    @Bean
    public TruckService truckService() {
        return new TruckService();
    }

    @Bean
    public UserCommandParserService userCommandParserService() {
        return new UserCommandParserService();
    }


    // Logistics
    @Bean
    public OnePerTruckLogisticService onePerTruckLogisticService(TruckService truckService) {
        return new OnePerTruckLogisticService(truckService);
    }

    @Bean
    public FullFillTruckLogisticService fullFillTruckLogisticService(TruckService truckService) {
        return new FullFillTruckLogisticService(truckService);
    }

    @Bean
    public BalancedFillTruckLogisticService balancedFillTruckLogisticService(TruckService truckService) {
        return new BalancedFillTruckLogisticService(truckService);
    }
}
