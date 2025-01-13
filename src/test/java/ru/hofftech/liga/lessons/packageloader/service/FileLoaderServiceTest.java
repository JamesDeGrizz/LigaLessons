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
    void getPackageNames_givenNonExistentFilePath_returnsEmptyList() {
        var packages = fileLoaderService.getPackageNames("non-existent-file");
        assertTrue(packages.isEmpty());
    }

    @Test
    void getPackageNames_givenEmptyFile_returnsEmptyList() {
        var packages = fileLoaderService.getPackageNames("empty_packages.txt");
        assertTrue(packages.isEmpty());
    }

    @Test
    void getPackageNames_givenCorrectFile_returnsFourPackageList() {
        var packages = fileLoaderService.getPackageNames("packages.txt");
        assertEquals(4, packages.size());
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