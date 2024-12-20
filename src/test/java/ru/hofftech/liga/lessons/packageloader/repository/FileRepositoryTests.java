package ru.hofftech.liga.lessons.packageloader.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileRepositoryTests {
    private FileLoaderService fileLoaderService;

    @BeforeEach
    public void setUp() {
        fileLoaderService = new FileLoaderService();
    }
    
    @Test
    void FileRepository_getPackages_fileNotFound() {
        var packages = fileLoaderService.getPackages("non-existent-file");
        assertTrue(packages.isEmpty());
    }

    @Test
    void FileRepository_getPackages_emptyFile() throws IOException {
        var packages = fileLoaderService.getPackages("empty_packages.txt");
        assertTrue(packages.isEmpty());
    }

    @Test
    void FileRepository_getPackages_ShouldBe4Packages() {
        var packages = fileLoaderService.getPackages("packages.txt");
        assertEquals(4, packages.size());
    }
}
