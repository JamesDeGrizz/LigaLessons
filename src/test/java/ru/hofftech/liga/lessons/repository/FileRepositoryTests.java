package ru.hofftech.liga.lessons.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileRepositoryTests {
    private Path testFilePath;
    private FileRepository fileRepository;

    @BeforeEach
    void setUp() throws IOException {
        fileRepository = new FileRepository();

        testFilePath = Files.createTempFile("packages_test", ".txt");
        List<String> lines = Arrays.asList(
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
        var packages = fileRepository.getPackages("non-existent-file");
        assertTrue(packages.isEmpty());
    }

    @Test
    void FileRepository_getPackages_emptyFile() throws IOException {
        Files.write(testFilePath, Arrays.asList());
        var packages = fileRepository.getPackages(testFilePath.toString());
        assertTrue(packages.isEmpty());
    }

    @Test
    void FileRepository_getPackages_ShouldBe4Packages() {
        var packages = fileRepository.getPackages(testFilePath.toString());
        assertEquals(4, packages.size());
    }
}
