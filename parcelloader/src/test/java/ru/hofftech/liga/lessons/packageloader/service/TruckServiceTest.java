package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class TruckServiceTest {

    @Test
    void canPlaceParcel_givenEmptyTruck_returnsTrue() {
        var truckService = new TruckService();
        var truck = new Truck(new TruckSize(6, 6));
        var parcel = new Parcel(List.of("333"), "test", '9', null);

        assertThat(truckService.canPlaceParcel(truck, parcel, 0, 0))
                .isTrue();
    }

    @Test
    void canPlaceParcel_givenFullTruck_returnsFalse() {
        var truckService = new TruckService();
        var truck = new Truck(new TruckSize(6, 6));
        truckService.placeParcel(truck, new Parcel(List.of("999", "999", "999"), "test", '9', null), 0, 0);
        truckService.placeParcel(truck, new Parcel(List.of("999", "999", "999"), "test", '9', null), 0, 3);
        truckService.placeParcel(truck, new Parcel(List.of("999", "999", "999"), "test", '9', null), 3, 0);
        truckService.placeParcel(truck, new Parcel(List.of("999", "999", "999"), "test", '9', null), 3, 3);

        var parcel = new Parcel(List.of("1"), "test", '9', null);

        IntStream.range(0, 6)
                .forEach(i -> IntStream.range(0, 6)
                        .forEach(j -> {
                            assertThat(truckService.canPlaceParcel(truck, parcel, i, j))
                                    .isFalse();
                        }));

    }

    @Test
    void placePackage_givenBigPackage_returnsCorrectlyPlacedParcel() {
        var truckService = new TruckService();
        var truck = new Truck(new TruckSize(6, 6));
        truckService.placeParcel(truck, new Parcel(List.of("999", "999", "999"), "test", '9', null), 2, 2);

        assertThat(truck.getContent()[2][2])
                .isEqualTo('9');
        assertThat(truck.getContent()[3][3])
                .isEqualTo('9');
        assertThat(truck.getContent()[4][4])
                .isEqualTo('9');
        assertThat(truck.getContent()[2][4])
                .isEqualTo('9');
        assertThat(truck.getContent()[4][2])
                .isEqualTo('9');

        assertThat(truck.getContent()[1][1])
                .isEqualTo(' ');
        assertThat(truck.getContent()[1][2])
                .isEqualTo(' ');
        assertThat(truck.getContent()[1][3])
                .isEqualTo(' ');
        assertThat(truck.getContent()[1][4])
                .isEqualTo(' ');
    }

    @Test
    void getFreeSpaceCount_givenSemiFilledTruck_returnsCorrectFreeSpaceCount() {
        var truckService = new TruckService();
        var truck = new Truck(new TruckSize(6, 6));
        truckService.placeParcel(truck, new Parcel(List.of("999", "999", "999"), "test", '9', null), 0, 0);

        assertThat(truckService.getFreeSpaceCount(truck))
                .isEqualTo(27);
    }

    @Test
    void getFreeSpaceCount_givenFullFilledTruck_returnsCorrectFreeSpaceCount() {
        var truckService = new TruckService();
        var truck = new Truck(new TruckSize(3, 3));
        truckService.placeParcel(truck, new Parcel(List.of("999", "999", "999"), "test", '9', null), 0, 0);

        assertThat(truckService.getFreeSpaceCount(truck))
                .isEqualTo(0);
    }

    @Test
    void getPackages_givenFullFilledTruck_returnsCorrectPackageList() {
        var truckService = new TruckService();
        var truck = new Truck(new TruckSize(3, 3));
        truckService.placeParcel(truck, new Parcel(List.of("999", "999", "999"), "test", '9', null), 0, 0);

        assertThat(truck.getParcels().size())
                .isEqualTo(1);
    }
}