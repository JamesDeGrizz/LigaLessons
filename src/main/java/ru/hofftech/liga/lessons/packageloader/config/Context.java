package ru.hofftech.liga.lessons.packageloader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.liga.lessons.packageloader.controller.TelegramController;
import ru.hofftech.liga.lessons.packageloader.model.configuration.BillingConfiguration;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.packageloader.repository.PackageRepository;
import ru.hofftech.liga.lessons.packageloader.service.BillingService;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportPackageService;
import ru.hofftech.liga.lessons.packageloader.service.ReportTruckService;
import ru.hofftech.liga.lessons.packageloader.service.TelegramService;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandParserService;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;
import ru.hofftech.liga.lessons.packageloader.service.command.CreatePackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.DeletePackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.EditPackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindPackageUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindUserOrdersCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadPackagesUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.UnloadTrucksUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.OnePerTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.validator.CreatePackageUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.DeletePackageUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.EditPackageUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.FindUserOrdersUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.LoadPackagesUserCommandValidator;
import ru.hofftech.liga.lessons.packageloader.validator.UnloadTrucksUserCommandValidator;

@Slf4j
@Configuration
@EnableConfigurationProperties(BillingConfiguration.class)
public class Context {
    // Repos
    @Bean
    public PackageRepository packageRepository() {
        return new PackageRepository();
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
            var botUsername = System.getenv("bot_username");
            var botToken = System.getenv("bot_token");
            // todo
            return new TelegramService(botUsername, botToken, userCommandProcessorService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


    // Validators
    @Bean
    public CreatePackageUserCommandValidator createPackageUserCommandValidator() {
        return new CreatePackageUserCommandValidator();
    }

    @Bean
    public EditPackageUserCommandValidator editPackageUserCommandValidator() {
        return new EditPackageUserCommandValidator();
    }

    @Bean
    public LoadPackagesUserCommandValidator loadPackagesUserCommandValidator() {
        return new LoadPackagesUserCommandValidator();
    }

    @Bean
    public UnloadTrucksUserCommandValidator unloadTrucksUserCommandValidator() {
        return new UnloadTrucksUserCommandValidator();
    }

    @Bean
    public DeletePackageUserCommandValidator deletePackageUserCommandValidator() {
        return new DeletePackageUserCommandValidator();
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
    public ReportPackageService reportPackageService() {
        return new ReportPackageService();
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
            ApplicationContext applicationContext) {
        return new UserCommandProcessorService(userCommandParserService, applicationContext);
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
    @Bean
    public CreatePackageUserCommandService createPackageUserCommandService(
            PackageRepository packageRepository,
            CreatePackageUserCommandValidator commandValidator) {
        return new CreatePackageUserCommandService(packageRepository, commandValidator);
    }

    @Bean
    public DeletePackageUserCommandService deletePackageUserCommandService(
            PackageRepository packageRepository,
            DeletePackageUserCommandValidator deletePackageUserCommandValidator) {
        return new DeletePackageUserCommandService(packageRepository, deletePackageUserCommandValidator);
    }

    @Bean
    public EditPackageUserCommandService editPackageUserCommandService(
            PackageRepository packageRepository,
            EditPackageUserCommandValidator commandValidator) {
        return new EditPackageUserCommandService(packageRepository, commandValidator);
    }

    @Bean
    public FindPackageUserCommandService findPackageUserCommandService(
            PackageRepository packageRepository) {
        return new FindPackageUserCommandService(packageRepository);
    }

    @Bean
    public LoadPackagesUserCommandService loadPackagesUserCommandService(
            FileLoaderService fileLoaderService,
            ReportTruckService reportTruckService,
            PackageRepository packageRepository,
            LoadPackagesUserCommandValidator commandValidator,
            BillingService billingService,
            ApplicationContext applicationContext) {
        return new LoadPackagesUserCommandService(
                fileLoaderService,
                reportTruckService,
                packageRepository,
                commandValidator,
                applicationContext,
                billingService);
    }

    @Bean
    public UnloadTrucksUserCommandService unloadTrucksUserCommandService(
            FileLoaderService fileLoaderService,
            ReportPackageService reportPackageService,
            BillingService billingService,
            UnloadTrucksUserCommandValidator commandValidator) {
        return new UnloadTrucksUserCommandService(
                fileLoaderService,
                reportPackageService,
                commandValidator,
                billingService);
    }

    @Bean
    public FindUserOrdersCommandService findUserOrdersCommandService(
            OrderRepository orderRepository,
            FindUserOrdersUserCommandValidator commandValidator) {
        return new FindUserOrdersCommandService(orderRepository, commandValidator);
    }
}
