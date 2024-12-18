package ru.hofftech.liga.lessons.service;

import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.model.Package;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TruckContentServiceTest {

    @Test
    void TruckContentService_canPlacePackage() {
        var service = TruckContentServiceFactory.getTruckContentService(6, 6);
        var pkg = new Package(Arrays.asList("333"));

        assertTrue(service.canPlacePackage(pkg, 0, 0));
    }
}