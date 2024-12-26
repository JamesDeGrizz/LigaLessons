package ru.hofftech.liga.lessons.packageloader.service.logistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FullFillTruckLogisticServiceTest {
    private FullFillTruckLogisticService fullFillTruckLogisticService;
    private TruckServiceFactory truckServiceFactory;

    @BeforeEach
    public void setUp() {
        truckServiceFactory = new TruckServiceFactory();
        fullFillTruckLogisticService = new FullFillTruckLogisticService(truckServiceFactory);
    }

    @Test
    void placePackagesToTrucks_given6Packages2Trucks_returns2Trucks() {
        var packages = Arrays.asList(
                new Package(Arrays.asList("999", "999", "999")),
                new Package(Arrays.asList("999", "999", "999")),
                new Package(Arrays.asList("666", "666")),
                new Package(Arrays.asList("666", "666")),
                new Package(Arrays.asList("333")),
                new Package(Arrays.asList("333"))
        );

        var trucks = fullFillTruckLogisticService.placePackagesToTrucks(packages, 2);

        assertThat(trucks)
                .isNotEmpty()
                .hasSize(2);

        char[][] expectedFirstTruckContent = {
                {'9', '9', '9', '9', '9', '9'},
                {'9', '9', '9', '9', '9', '9'},
                {'9', '9', '9', '9', '9', '9'},
                {'6', '6', '6', '6', '6', '6'},
                {'6', '6', '6', '6', '6', '6'},
                {'3', '3', '3', '3', '3', '3'}
        };

        assertThat(trucks.getFirst().getContent())
                .isDeepEqualTo(expectedFirstTruckContent);

        char[][] expectedSecondTruckContent = {
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' '}
        };

        assertThat(trucks.getLast().getContent())
                .isDeepEqualTo(expectedSecondTruckContent);
    }

    @Test
    void placePackagesToTrucks_given8Packages1Truck_returnsException() {
        var packages = Arrays.asList(
                new ru.hofftech.liga.lessons.packageloader.model.Package(Arrays.asList("999", "999", "999")),
                new ru.hofftech.liga.lessons.packageloader.model.Package(Arrays.asList("999", "999", "999")),
                new ru.hofftech.liga.lessons.packageloader.model.Package(Arrays.asList("666", "666")),
                new ru.hofftech.liga.lessons.packageloader.model.Package(Arrays.asList("666", "666")),
                new ru.hofftech.liga.lessons.packageloader.model.Package(Arrays.asList("333")),
                new ru.hofftech.liga.lessons.packageloader.model.Package(Arrays.asList("333")),
                new ru.hofftech.liga.lessons.packageloader.model.Package(Arrays.asList("1")),
                new Package(Arrays.asList("1"))
        );

        assertThatThrownBy(() -> fullFillTruckLogisticService.placePackagesToTrucks(packages, 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}