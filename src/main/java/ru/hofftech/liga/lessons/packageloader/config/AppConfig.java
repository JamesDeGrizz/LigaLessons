package ru.hofftech.liga.lessons.packageloader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.liga.lessons.packageloader.controller.TelegramController;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.BillingService;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportParcelService;
import ru.hofftech.liga.lessons.packageloader.service.ReportTruckService;
import ru.hofftech.liga.lessons.packageloader.service.TelegramService;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandParserService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;
import ru.hofftech.liga.lessons.packageloader.service.command.CreateParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.DeleteParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.EditParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindUserOrdersCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadParcelsUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.UnloadTrucksUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.OnePerTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.validator.CreateParcelUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.DeleteParcelUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.EditParcelUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.FindUserOrdersUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.LoadParcelsUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.UnloadTrucksUserCommandValidator;

import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties(BillingConfiguration.class)
public class AppConfig {
    @Value("${telegram.credentials.username}")
    private String botUsername;

    @Value("${telegram.credentials.token}")
    private String botToken;
    private final String qwe = "qwe";

    // Repos
    @Bean
    public ParcelRepository parcelRepository() {
        return new ParcelRepository();
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository();
    }


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

    @Bean
    public UserCommandProcessorService userCommandProcessorService(
            UserCommandParserService userCommandParserService,
            Map<String, UserCommandService> userCommandServices) {
        return new UserCommandProcessorService(userCommandParserService, userCommandServices);
    }

    @Bean
    public BillingService billingService(
            OrderRepository orderRepository,
            BillingConfiguration billingConfiguration) {
        return new BillingService(orderRepository, billingConfiguration);
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


    // Commands
    @Bean(BeanNameConfig.CREATE_PARCEL)
    public CreateParcelUserCommandService createParcelUserCommandService(
            ParcelRepository parcelRepository,
            CreateParcelUserCommandValidator commandValidator) {
        return new CreateParcelUserCommandService(parcelRepository, commandValidator);
    }

    @Bean(BeanNameConfig.DELETE_PARCEL)
    public DeleteParcelUserCommandService deleteParcelUserCommandService(
            ParcelRepository parcelRepository,
            DeleteParcelUserCommandValidator deleteParcelUserCommandValidator) {
        return new DeleteParcelUserCommandService(parcelRepository, deleteParcelUserCommandValidator);
    }

    @Bean(BeanNameConfig.EDIT_PARCEL)
    public EditParcelUserCommandService editParcelUserCommandService(
            ParcelRepository parcelRepository,
            EditParcelUserCommandValidator commandValidator) {
        return new EditParcelUserCommandService(parcelRepository, commandValidator);
    }

    @Bean(BeanNameConfig.FIND_PARCEL)
    public FindParcelUserCommandService findParcelUserCommandService(
            ParcelRepository parcelRepository) {
        return new FindParcelUserCommandService(parcelRepository);
    }

    @Bean(BeanNameConfig.LOAD_PARCELS)
    public LoadParcelsUserCommandService loadParcelsUserCommandService(
            FileLoaderService fileLoaderService,
            ReportTruckService reportTruckService,
            ParcelRepository parcelRepository,
            LoadParcelsUserCommandValidator commandValidator,
            BillingService billingService,
            ApplicationContext applicationContext) {
        return new LoadParcelsUserCommandService(
                fileLoaderService,
                reportTruckService,
                parcelRepository,
                commandValidator,
                applicationContext,
                billingService);
    }

    @Bean(BeanNameConfig.UNLOAD_TRUCKS)
    public UnloadTrucksUserCommandService unloadTrucksUserCommandService(
            FileLoaderService fileLoaderService,
            ReportParcelService reportParcelService,
            BillingService billingService,
            UnloadTrucksUserCommandValidator commandValidator) {
        return new UnloadTrucksUserCommandService(
                fileLoaderService,
                reportParcelService,
                commandValidator,
                billingService);
    }

    @Bean(BeanNameConfig.SHOW_ORDERS)
    public FindUserOrdersCommandService findUserOrdersCommandService(
            OrderRepository orderRepository,
            FindUserOrdersUserCommandValidator commandValidator) {
        return new FindUserOrdersCommandService(orderRepository, commandValidator);
    }
}
