package ru.hofftech.liga.lessons.telegramclient.service.logistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.telegramclient.model.Parcel;
import ru.hofftech.liga.lessons.telegramclient.model.TruckSize;
import ru.hofftech.liga.lessons.telegramclient.service.TruckService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OnePerTruckLogisticServiceTest {
    private OnePerTruckLogisticService onePerTruckLogisticService;
    private TruckService truckService;

    @BeforeEach
    public void setUp() {
        truckService = new TruckService();
        onePerTruckLogisticService = new OnePerTruckLogisticService(truckService);
    }

    @Test
    void placePackagesToTrucks_given6Parcels6Trucks_returns6Trucks() {
        var packages = Arrays.asList(
                new Parcel(Arrays.asList("999", "999", "999"), "test", '9'),
                new Parcel(Arrays.asList("999", "999", "999"), "test", '9'),
                new Parcel(Arrays.asList("666", "666"), "test", '9'),
                new Parcel(Arrays.asList("666", "666"), "test", '9'),
                new Parcel(Arrays.asList("333"), "test", '9'),
                new Parcel(Arrays.asList("1"), "test", '9')
        );

        var trucks = onePerTruckLogisticService.placeParcelsToTrucks(packages,
                List.of(
                        new TruckSize(6, 6),
                        new TruckSize(6, 6),
                        new TruckSize(6, 6),
                        new TruckSize(6, 6),
                        new TruckSize(6, 6),
                        new TruckSize(6, 6)
                ));

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
    void placePackagesToTrucks_given8Parcels6Trucks_returnsException() {
        var packages = Arrays.asList(
                new Parcel(Arrays.asList("999", "999", "999"), "test", '9'),
                new Parcel(Arrays.asList("999", "999", "999"), "test", '9'),
                new Parcel(Arrays.asList("666", "666"), "test", '9'),
                new Parcel(Arrays.asList("666", "666"), "test", '9'),
                new Parcel(Arrays.asList("333"), "test", '9'),
                new Parcel(Arrays.asList("333"), "test", '9'),
                new Parcel(Arrays.asList("1"), "test", '9'),
                new Parcel(Arrays.asList("1"), "test", '9')
        );

        assertThatThrownBy(() -> onePerTruckLogisticService.placeParcelsToTrucks(packages, List.of(new TruckSize(6, 6))))
                .isInstanceOf(IllegalArgumentException.class);
    }
}