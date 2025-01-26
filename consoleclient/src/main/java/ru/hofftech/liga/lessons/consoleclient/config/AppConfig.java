package ru.hofftech.liga.lessons.consoleclient.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.liga.lessons.consoleclient.service.FileLoaderService;
import ru.hofftech.liga.lessons.consoleclient.service.ReportParcelService;
import ru.hofftech.liga.lessons.consoleclient.service.ReportTruckService;
import ru.hofftech.liga.lessons.consoleclient.service.TruckService;
import ru.hofftech.liga.lessons.consoleclient.service.UserCommandParserService;
import ru.hofftech.liga.lessons.consoleclient.service.UserCommandProcessorService;
import ru.hofftech.liga.lessons.consoleclient.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.consoleclient.service.logistic.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.consoleclient.service.logistic.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.consoleclient.service.logistic.OnePerTruckLogisticService;
import ru.hofftech.liga.lessons.consoleclient.validator.CreateParcelUserCommandValidator;
import ru.hofftech.liga.lessons.consoleclient.validator.DeleteParcelUserCommandValidator;
import ru.hofftech.liga.lessons.consoleclient.validator.EditParcelUserCommandValidator;
import ru.hofftech.liga.lessons.consoleclient.validator.FindUserOrdersUserCommandValidator;
import ru.hofftech.liga.lessons.consoleclient.validator.LoadParcelsUserCommandValidator;
import ru.hofftech.liga.lessons.consoleclient.validator.UnloadTrucksUserCommandValidator;

import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties(BillingConfiguration.class)
public class AppConfig {
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

    @Bean
    public UserCommandProcessorService userCommandProcessorService(
            UserCommandParserService userCommandParserService,
            Map<String, UserCommandService> userCommandServices) {
        return new UserCommandProcessorService(userCommandParserService, userCommandServices);
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
