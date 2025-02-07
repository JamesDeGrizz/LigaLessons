package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileLoaderServiceTest {
    private FileLoaderService fileLoaderService;

    @BeforeEach
    void setUp() {
        fileLoaderService = new FileLoaderService();
    }

    @Test
    void getParcelNames_givenNonExistentFilePath_returnsEmptyList() {
        var parcels = fileLoaderService.getParcelNames("non-existent-file");
        assertThat(parcels).isNull();
    }

    @Test
    void getParcelNames_givenEmptyFile_returnsEmptyList() {
        var parcels = fileLoaderService.getParcelNames("empty_parcels.txt");
        assertThat(parcels).isEmpty();
    }

    @Test
    void getPackageNames_givenCorrectFile_returnsFourParcelList() {
        var parcels = fileLoaderService.getParcelNames("parcels.txt");
        assertThat(parcels).hasSize(5);
    }

    @Test
    void getTrucks_givenNonExistentFilePath_returnsEmptyList() {
        var trucks = fileLoaderService.getTrucks("non-existent-file");
        assertThat(trucks).isEmpty();
    }

    @Test
    void getTrucks_givenEmptyFile_returnsEmptyList() {
        var trucks = fileLoaderService.getTrucks("empty_trucks.json");
        assertThat(trucks).isEmpty();
    }

    @Test
    void getTrucks_givenCorrectFile_returnsFourPackageList() {
        var trucks = fileLoaderService.getTrucks("trucks.json");
        assertThat(trucks).hasSize(3);
    }
}