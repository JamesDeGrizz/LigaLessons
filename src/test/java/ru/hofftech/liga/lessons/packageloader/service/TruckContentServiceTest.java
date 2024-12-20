package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TruckContentServiceTest {

    @Test
    void TruckContentService_canPlacePackage() {
        var truckFactory = new TruckServiceFactory();
        var service = truckFactory.getTruckService(6, 6);
        var pkg = new Package(List.of("333"));

        assertTrue(service.canPlacePackage(pkg, 0, 0));
    }
}