package ru.hofftech.liga.lessons.parcelloader.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.liga.lessons.parcelloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.parcelloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.parcelloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.parcelloader.service.FileLoaderService;
import ru.hofftech.liga.lessons.parcelloader.service.LoadParcelsService;
import ru.hofftech.liga.lessons.parcelloader.service.ReportParcelService;
import ru.hofftech.liga.lessons.parcelloader.service.ReportTruckService;
import ru.hofftech.liga.lessons.parcelloader.service.TruckService;
import ru.hofftech.liga.lessons.parcelloader.service.UnloadTrucksService;
import ru.hofftech.liga.lessons.parcelloader.service.logistic.LogisticAlgorithmResolver;
import ru.hofftech.liga.lessons.parcelloader.service.logistic.impl.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.parcelloader.service.logistic.impl.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.parcelloader.service.logistic.impl.OnePerTruckLogisticService;
import ru.hofftech.liga.lessons.parcelloader.validator.LoadParcelsUserCommandValidator;
import ru.hofftech.liga.lessons.parcelloader.validator.UnloadTrucksUserCommandValidator;

@Slf4j
@Configuration
public class AppConfig {
    @Bean
    public LoadParcelsUserCommandValidator loadParcelsUserCommandValidator() {
        return new LoadParcelsUserCommandValidator();
    }

    @Bean
    public UnloadTrucksUserCommandValidator unloadTrucksUserCommandValidator() {
        return new UnloadTrucksUserCommandValidator();
    }


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

    @Bean
    public LoadParcelsService loadParcelsUserCommandService(
            FileLoaderService fileLoaderService,
            ReportTruckService reportTruckService,
            ParcelRepository parcelRepository,
            LoadParcelsUserCommandValidator commandValidator,
            LogisticAlgorithmResolver logisticAlgorithmResolver,
            ParcelMapper parcelMapper,
            OrderRepository orderRepository) {
        return new LoadParcelsService(
                fileLoaderService,
                reportTruckService,
                parcelRepository,
                commandValidator,
                logisticAlgorithmResolver,
                parcelMapper,
                orderRepository);
    }

    @Bean
    public UnloadTrucksService unloadTrucksUserCommandService(
            FileLoaderService fileLoaderService,
            ReportParcelService reportParcelService,
            OrderRepository orderRepository,
            UnloadTrucksUserCommandValidator commandValidator) {
        return new UnloadTrucksService(
                fileLoaderService,
                reportParcelService,
                commandValidator,
                orderRepository);
    }
}
