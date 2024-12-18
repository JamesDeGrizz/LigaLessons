package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.model.Package;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TruckContentServiceTest {

    @Test
    void TruckContentService_canPlacePackage() {
        var service = TruckContentServiceFactory.getTruckContentService(6, 6);
        var pkg = new Package(List.of("333"));

        assertTrue(service.canPlacePackage(pkg, 0, 0));
    }
}