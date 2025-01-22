package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileLoaderServiceTest {
    private FileLoaderService fileLoaderService;

    @BeforeEach
    public void setUp() {
        fileLoaderService = new FileLoaderService();
    }

    @Test
    void getParcelNames_givenNonExistentFilePath_returnsEmptyList() {
        var parcels = fileLoaderService.getParcelNames("non-existent-file");
        assertNull(parcels);
    }

    @Test
    void getParcelNames_givenEmptyFile_returnsEmptyList() {
        var parcels = fileLoaderService.getParcelNames("empty_parcels.txt");
        assertEquals(0, parcels.length);
    }

    @Test
    void getPackageNames_givenCorrectFile_returnsFourParcelList() {
        var parcels = fileLoaderService.getParcelNames("parcels.txt");
        assertEquals(5, parcels.length);
    }

    @Test
    void getTrucks_givenNonExistentFilePath_returnsEmptyList() {
        var trucks = fileLoaderService.getTrucks("non-existent-file");
        assertTrue(trucks.isEmpty());
    }

    @Test
    void getTrucks_givenEmptyFile_returnsEmptyList() {
        var trucks = fileLoaderService.getTrucks("empty_trucks.json");
        assertTrue(trucks.isEmpty());
    }

    @Test
    void getTrucks_givenCorrectFile_returnsFourPackageList() {
        var trucks = fileLoaderService.getTrucks("trucks.json");
        assertEquals(3, trucks.size());
    }
}