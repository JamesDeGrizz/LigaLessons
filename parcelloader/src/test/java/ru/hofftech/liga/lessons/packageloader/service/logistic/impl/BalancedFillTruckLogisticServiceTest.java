package ru.hofftech.liga.lessons.packageloader.service.logistic.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BalancedFillTruckLogisticServiceTest {
    private BalancedFillTruckLogisticService balancedFillTruckLogisticService;
    private TruckService truckService;

    @BeforeEach
    public void setUp() {
        truckService = new TruckService();
        balancedFillTruckLogisticService = new BalancedFillTruckLogisticService(truckService);
    }

    @Test
    void placePackagesToTrucks_given8Parcels2Trucks_returns2Trucks() {
        var packages = Arrays.asList(
                new Parcel(Arrays.asList("999", "999", "999"), "test", '9', null),
                new Parcel(Arrays.asList("999", "999", "999"), "test", '9', null),
                new Parcel(Arrays.asList("666", "666"), "test", '9', null),
                new Parcel(Arrays.asList("666", "666"), "test", '9', null),
                new Parcel(Arrays.asList("333"), "test", '9', null),
                new Parcel(Arrays.asList("333"), "test", '9', null),
                new Parcel(Arrays.asList("1"), "test", '9', null),
                new Parcel(Arrays.asList("1"), "test", '9', null)
        );

        var trucks = balancedFillTruckLogisticService.placeParcelsToTrucks(packages, List.of(new TruckSize(6, 6), new TruckSize(6, 6)));

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
    void placePackagesToTrucks_given8Parcels1Truck_returnsException() {
        var packages = Arrays.asList(
                new Parcel(Arrays.asList("999", "999", "999"), "test", '9', null),
                new Parcel(Arrays.asList("999", "999", "999"), "test", '9', null),
                new Parcel(Arrays.asList("666", "666"), "test", '9', null),
                new Parcel(Arrays.asList("666", "666"), "test", '9', null),
                new Parcel(Arrays.asList("333"), "test", '9', null),
                new Parcel(Arrays.asList("333"), "test", '9', null),
                new Parcel(Arrays.asList("1"), "test", '9', null),
                new Parcel(Arrays.asList("1"), "test", '9', null)
        );

        assertThatThrownBy(() -> balancedFillTruckLogisticService.placeParcelsToTrucks(packages, List.of(new TruckSize(6, 6))))
                .isInstanceOf(IllegalArgumentException.class);
    }
}