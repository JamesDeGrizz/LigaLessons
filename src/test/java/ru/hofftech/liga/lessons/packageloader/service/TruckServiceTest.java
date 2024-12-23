package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Package;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class TruckServiceTest {

    @Test
    void canPlacePackage_givenEmptyTruck_returnsTrue() {
        var truckService = new TruckService(6, 6);
        var pkg = new Package(List.of("333"));

        assertThat(truckService.canPlacePackage(pkg, 0, 0))
                .isTrue();
    }

    @Test
    void canPlacePackage_givenFullTruck_returnsFalse() {
        var truckService = new TruckService(6, 6);
        truckService.placePackage(new Package(List.of("999", "999", "999")), 0, 0);
        truckService.placePackage(new Package(List.of("999", "999", "999")), 0, 3);
        truckService.placePackage(new Package(List.of("999", "999", "999")), 3, 0);
        truckService.placePackage(new Package(List.of("999", "999", "999")), 3, 3);

        var pkg = new Package(List.of("1"));

        IntStream.range(0, 6)
                .forEach(i -> IntStream.range(0, 6)
                        .forEach(j -> {
                            assertThat(truckService.canPlacePackage(pkg, i, j))
                                    .isFalse();
                        }));

    }

    @Test
    void placePackage_givenBigPackage_returnsCorrectlyPlacedPackage() {
        var truckService = new TruckService(6, 6);
        truckService.placePackage(new Package(List.of("999", "999", "999")), 2, 2);

        assertThat(truckService.getTruck().getContent()[2][2])
                .isEqualTo('9');
        assertThat(truckService.getTruck().getContent()[3][3])
                .isEqualTo('9');
        assertThat(truckService.getTruck().getContent()[4][4])
                .isEqualTo('9');
        assertThat(truckService.getTruck().getContent()[2][4])
                .isEqualTo('9');
        assertThat(truckService.getTruck().getContent()[4][2])
                .isEqualTo('9');

        assertThat(truckService.getTruck().getContent()[1][1])
                .isEqualTo(' ');
        assertThat(truckService.getTruck().getContent()[1][2])
                .isEqualTo(' ');
        assertThat(truckService.getTruck().getContent()[1][3])
                .isEqualTo(' ');
        assertThat(truckService.getTruck().getContent()[1][4])
                .isEqualTo(' ');
    }

    @Test
    void getFreeSpaceCount_givenSemiFilledTruck_returnsCorrectFreeSpaceCount() {
        var truckService = new TruckService(6, 6);
        truckService.placePackage(new Package(List.of("999", "999", "999")), 0, 0);

        assertThat(truckService.getFreeSpaceCount())
                .isEqualTo(27);
    }

    @Test
    void getFreeSpaceCount_givenFullFilledTruck_returnsCorrectFreeSpaceCount() {
        var truckService = new TruckService(3, 3);
        truckService.placePackage(new Package(List.of("999", "999", "999")), 0, 0);

        assertThat(truckService.getFreeSpaceCount())
                .isEqualTo(0);
    }

    @Test
    void getPackages_givenFullFilledTruck_returnsCorrectPackageList() {
        var truckService = new TruckService(3, 3);
        truckService.placePackage(new Package(List.of("999", "999", "999")), 0, 0);

        assertThat(truckService.getPackages().size())
                .isEqualTo(1);
        // todo: проверить по содержимому
    }
}