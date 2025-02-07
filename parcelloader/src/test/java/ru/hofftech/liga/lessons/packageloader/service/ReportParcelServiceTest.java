package ru.hofftech.liga.lessons.packageloader.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportParcelServiceTest {
    private final ReportParcelService service = new ReportParcelService();

    @TempDir
    Path tempDir;

    private List<Parcel> createTestParcels() {
        return List.of(
                new Parcel(List.of("XX"), "Box", 'C', null),
                new Parcel(List.of("X"), "Envelope", 'C', null),
                new Parcel(List.of("XXX"), "Box", 'C', null)
        );
    }

    @Test
    void saveParcelsToFile_ShouldCreateFileWithCorrectContent() throws IOException {
        Path testFile = tempDir.resolve("test_report.csv");

        service.saveParcelsToFile(
                testFile.toString(),
                createTestParcels(),
                true
        );

        String content = Files.readString(testFile);
        assertAll(
                () -> assertTrue(content.contains("Box;2")),
                () -> assertTrue(content.contains("Envelope;1"))
        );
    }

    @Test
    void saveParcelsToFile_ShouldCreateSimpleFileWhenCountDisabled() throws IOException {
        Path testFile = tempDir.resolve("simple_report.txt");

        service.saveParcelsToFile(
                testFile.toString(),
                createTestParcels(),
                false
        );

        String content = Files.readString(testFile);
        assertAll(
                () -> assertTrue(content.contains("Box")),
                () -> assertTrue(content.contains("Envelope")),
                () -> assertFalse(content.contains(";"))
        );
    }


}