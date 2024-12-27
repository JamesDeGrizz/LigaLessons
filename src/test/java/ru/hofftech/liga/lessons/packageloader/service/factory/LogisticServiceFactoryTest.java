package ru.hofftech.liga.lessons.packageloader.service.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.packageloader.service.logistic.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.OnePerTruckLogisticService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LogisticServiceFactoryTest {
    LogisticServiceFactory logisticServiceFactory;

    @BeforeEach
    void setUpOnce() {
        logisticServiceFactory = new LogisticServiceFactory(new TruckServiceFactory());
    }

    @Test
    void getLogisticService_givenOnePerTruckPlacingAlgorithm_returnsOnePerTruckLogisticService() {
        var logisticService = logisticServiceFactory.getLogisticService(PlacingAlgorithm.OnePerTruck);
        assertThat(logisticService)
                .isInstanceOf(OnePerTruckLogisticService.class);
    }

    @Test
    void getLogisticService_givenFillTruckPlacingAlgorithm_returnsFullFillTruckLogisticService() {
        var logisticService = logisticServiceFactory.getLogisticService(PlacingAlgorithm.FillTruck);
        assertThat(logisticService)
                .isInstanceOf(FullFillTruckLogisticService.class);
    }

    @Test
    void getLogisticService_givenBalancedPlacingAlgorithm_returnsBalancedFillTruckLogisticService() {
        var logisticService = logisticServiceFactory.getLogisticService(PlacingAlgorithm.Balanced);
        assertThat(logisticService)
                .isInstanceOf(BalancedFillTruckLogisticService.class);
    }

    @Test
    void getLogisticService_givenNoneOfPlacingAlgorithm_returnsIllegalArgumentException() {
        assertThatThrownBy(() -> logisticServiceFactory.getLogisticService(PlacingAlgorithm.NoneOf))
                .isInstanceOf(IllegalArgumentException.class);

    }
}