package ru.hofftech.liga.lessons.packageloader.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hofftech.liga.lessons.packageloader.service.FileLoaderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileRepositoryTests {
    private Path testFilePath;
    private FileLoaderService fileLoaderService;

    @BeforeEach
    void setUp() throws IOException {
        fileLoaderService = new FileLoaderService();

        testFilePath = Files.createTempFile("packages_test", ".txt");
        List<String> lines = List.of(
                "1",
                "",
                "22",
                "",
                "333",
                "",
                "999",
                "999",
                "999"
        );

        Files.write(testFilePath, lines);
    }

    @AfterEach
    void tearDown() throws IOException {
        try {
            Thread.sleep(100); // Задержка в 100 миллисекунд
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        finally {
            Files.deleteIfExists(testFilePath);
        }
    }

    @Test
    void FileRepository_getPackages_fileNotFound() {
        var packages = fileLoaderService.getPackages("non-existent-file");
        assertTrue(packages.isEmpty());
    }

    @Test
    void FileRepository_getPackages_emptyFile() throws IOException {
        Files.write(testFilePath, Collections.emptyList());
        var packages = fileLoaderService.getPackages(testFilePath.toString());
        assertTrue(packages.isEmpty());
    }

    @Test
    void FileRepository_getPackages_ShouldBe4Packages() {
        var packages = fileLoaderService.getPackages(testFilePath.toString());
        assertEquals(4, packages.size());
    }
}
