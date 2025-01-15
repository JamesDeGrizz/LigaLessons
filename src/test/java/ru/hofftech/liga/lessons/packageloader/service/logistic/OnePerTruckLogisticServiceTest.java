package ru.hofftech.liga.lessons.packageloader.service.logistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Package;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OnePerTruckLogisticServiceTest {
    private OnePerTruckLogisticService onePerTruckLogisticService;
    private TruckServiceFactory truckServiceFactory;

    @BeforeEach
    public void setUp() {
        truckServiceFactory = new TruckServiceFactory();
        onePerTruckLogisticService = new OnePerTruckLogisticService(truckServiceFactory);
    }

    @Test
    void placePackagesToTrucks_given6Packages6Trucks_returns6Trucks() {
        var packages = Arrays.asList(
                new Package(Arrays.asList("999", "999", "999"), "test", '9'),
                new Package(Arrays.asList("999", "999", "999"), "test", '9'),
                new Package(Arrays.asList("666", "666"), "test", '9'),
                new Package(Arrays.asList("666", "666"), "test", '9'),
                new Package(Arrays.asList("333"), "test", '9'),
                new Package(Arrays.asList("1"), "test", '9')
        );

        var trucks = onePerTruckLogisticService.placePackagesToTrucks(packages, 6);

        assertThat(trucks)
                .isNotEmpty()
                .hasSize(6);

        char[][] expectedFirstTruckContent = {
                {'9', '9', '9', ' ', ' ', ' '},
                {'9', '9', '9', ' ', ' ', ' '},
                {'9', '9', '9', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };

        assertThat(trucks.getFirst().getContent())
                .isDeepEqualTo(expectedFirstTruckContent);

        char[][] expectedLastTruckContent = {
                {'1', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };

        assertThat(trucks.getLast().getContent())
                .isDeepEqualTo(expectedLastTruckContent);
    }

    @Test
    void placePackagesToTrucks_given8Packages6Trucks_returnsException() {
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

        assertThatThrownBy(() -> onePerTruckLogisticService.placePackagesToTrucks(packages, 6))
                .isInstanceOf(IllegalArgumentException.class);
    }
}