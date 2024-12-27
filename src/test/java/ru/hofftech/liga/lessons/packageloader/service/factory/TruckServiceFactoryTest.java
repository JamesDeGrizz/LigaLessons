package ru.hofftech.liga.lessons.packageloader.service.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import static org.assertj.core.api.Assertions.assertThat;

class TruckServiceFactoryTest {
    TruckServiceFactory truckServiceFactory;

    @BeforeEach
    void setUpOnce() {
        truckServiceFactory = new TruckServiceFactory();
    }

    @Test
    void getTruckService_givenContentSize_returnsCorrectTruck() {
        var truckService = truckServiceFactory.getTruckService(3, 3);
        assertThat(truckService.getPackages())
                .isEmpty();
        assertThat(truckService.getFreeSpaceCount())
                .isEqualTo(9);
        assertThat(truckService.getTruck())
                .isInstanceOf(Truck.class);
    }

    @Test
    void getTruckService_givenSemiFilledTruck_returnsCorrectTruck() {
        var truck = new Truck(4, 4);
        truck.getContent()[0][0] = '1';

        var truckService = truckServiceFactory.getTruckService(truck);
        assertThat(truckService.getPackages().size())
                .isEqualTo(1);
        assertThat(truckService.getFreeSpaceCount())
                .isEqualTo(15);
        assertThat(truckService.getTruck())
                .isInstanceOf(Truck.class);
    }
}