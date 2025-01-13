package ru.hofftech.liga.lessons.packageloader.service.logistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BalancedFillTruckLogisticServiceTest {
    private BalancedFillTruckLogisticService balancedFillTruckLogisticService;
    private TruckServiceFactory truckServiceFactory;

    @BeforeEach
    public void setUp() {
        truckServiceFactory = new TruckServiceFactory();
        balancedFillTruckLogisticService = new BalancedFillTruckLogisticService(truckServiceFactory);
    }

    @Test
    void placePackagesToTrucks_given8Packages2Trucks_returns2Trucks() {
        var packages = Arrays.asList(
                new Package(Arrays.asList("999", "999", "999"), "test", '9'),
                new Package(Arrays.asList("999", "999", "999"), "test", '9'),
                new Package(Arrays.asList("666", "666"), "test", '9'),
                new Package(Arrays.asList("666", "666"), "test", '9'),
                new Package(Arrays.asList("333"), "test", '9'),
                new Package(Arrays.asList("333"), "test", '9'),
                new Package(Arrays.asList("1"), "test", '9'),
                new Package(Arrays.asList("1"), "test", '9')
        );

        var trucks = balancedFillTruckLogisticService.placePackagesToTrucks(packages, 2);

        assertThat(trucks)
                .isNotEmpty()
                .hasSize(2);

        char[][] expectedTruckContent = {
                {'9', '9', '9', '6', '6', '6'},
                {'9', '9', '9', '6', '6', '6'},
                {'9', '9', '9', '3', '3', '3'},
                {'1', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };

        assertThat(trucks.getFirst().getContent())
                .isDeepEqualTo(expectedTruckContent);
        assertThat(trucks.getLast().getContent())
                .isDeepEqualTo(expectedTruckContent);
    }

    @Test
    void placePackagesToTrucks_given8Packages1Truck_returnsException() {
        var packages = Arrays.asList(
                new Package(Arrays.asList("999", "999", "999"), "test", '9'),
                new Package(Arrays.asList("999", "999", "999"), "test", '9'),
                new Package(Arrays.asList("666", "666"), "test", '9'),
                new Package(Arrays.asList("666", "666"), "test", '9'),
                new Package(Arrays.asList("333"), "test", '9'),
                new Package(Arrays.asList("333"), "test", '9'),
                new Package(Arrays.asList("1"), "test", '9'),
                new Package(Arrays.asList("1"), "test", '9')
        );

        assertThatThrownBy(() -> balancedFillTruckLogisticService.placePackagesToTrucks(packages, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}