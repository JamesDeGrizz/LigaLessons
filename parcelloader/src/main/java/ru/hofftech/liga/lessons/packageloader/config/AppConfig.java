package ru.hofftech.liga.lessons.packageloader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.liga.lessons.packageloader.mapper.OrderMapper;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.BillingService;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.packageloader.service.ReportParcelService;
import ru.hofftech.liga.lessons.packageloader.service.ReportTruckService;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.command.CreateParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.DeleteParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.EditParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindUserOrdersCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadParcelsUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.UnloadTrucksUserCommandService;
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
    public BillingService billingService(
            OrderRepository orderRepository,
            BillingConfiguration billingConfiguration,
            OrderMapper orderMapper) {
        return new BillingService(orderRepository, billingConfiguration, orderMapper);
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
    public CreateParcelUserCommandService createParcelUserCommandService(
            ParcelRepository parcelRepository,
            CreateParcelUserCommandValidator commandValidator,
            ParcelMapper parcelMapper) {
        return new CreateParcelUserCommandService(parcelRepository, commandValidator, parcelMapper);
    }

    @Bean
    public DeleteParcelUserCommandService deleteParcelUserCommandService(
            ParcelRepository parcelRepository,
            DeleteParcelUserCommandValidator deleteParcelUserCommandValidator) {
        return new DeleteParcelUserCommandService(parcelRepository, deleteParcelUserCommandValidator);
    }

    @Bean
    public EditParcelUserCommandService editParcelUserCommandService(
            ParcelRepository parcelRepository,
            EditParcelUserCommandValidator commandValidator) {
        return new EditParcelUserCommandService(parcelRepository, commandValidator);
    }

    @Bean
    public FindParcelUserCommandService findParcelUserCommandService(
            ParcelRepository parcelRepository,
            ParcelMapper parcelMapper) {
        return new FindParcelUserCommandService(parcelRepository, parcelMapper);
    }

    @Bean
    public LoadParcelsUserCommandService loadParcelsUserCommandService(
            FileLoaderService fileLoaderService,
            ReportTruckService reportTruckService,
            ParcelRepository parcelRepository,
            LoadParcelsUserCommandValidator commandValidator,
            BillingService billingService,
            ApplicationContext applicationContext,
            ParcelMapper parcelMapper) {
        return new LoadParcelsUserCommandService(
                fileLoaderService,
                reportTruckService,
                parcelRepository,
                commandValidator,
                applicationContext,
                billingService,
                parcelMapper);
    }

    @Bean
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

    @Bean
    public FindUserOrdersCommandService findUserOrdersCommandService(
            OrderRepository orderRepository,
            FindUserOrdersUserCommandValidator commandValidator,
            OrderMapper orderMapper) {
        return new FindUserOrdersCommandService(orderRepository, commandValidator, orderMapper);
    }
}
