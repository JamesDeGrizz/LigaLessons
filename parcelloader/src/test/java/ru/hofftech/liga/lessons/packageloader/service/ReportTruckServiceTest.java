package ru.hofftech.liga.lessons.packageloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportTruckServiceTest {
    private ReportTruckService reportTruckService;
    private Logger logger;
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<String> logCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logger = mock(Logger.class);
        objectMapper = mock(ObjectMapper.class);
        reportTruckService = new ReportTruckService();
    }

    @Test
    void testReportTruckContent() {
        char[][] content = {
                {'A', 'B', 'C'},
                {'D', 'E', 'F'},
                {'G', 'H', 'I'}
        };

        reportTruckService.reportTruckContent(content);

        verify(logger).info(logCaptor.capture());
        String logOutput = logCaptor.getValue();

        String expectedOutput = "\n" +
                "+GHI+\n" +
                "+DEF+\n" +
                "+ABC+\n" +
                "+++++\n";

        assertEquals(expectedOutput, logOutput);
    }

    @Test
    void testSaveTrucksToFile() throws IOException {
        String fileName = "testFile.json";
        List<Truck> trucks = Arrays.asList(
                new Truck(new TruckSize(3, 3)),
                new Truck(new TruckSize(4, 4))
        );

        doNothing().when(objectMapper).writeValue(any(File.class), eq(trucks));

        reportTruckService.saveTrucksToFile(fileName, trucks);

        verify(objectMapper).writeValue(any(File.class), eq(trucks));
        verify(logger).info("Грузовики успешно сохранены в файл " + fileName);
    }

    @Test
    void testSaveTrucksToFileIOException() throws IOException {
        String fileName = "testFile.json";
        List<Truck> trucks = Arrays.asList(
                new Truck(new TruckSize(3, 3)),
                new Truck(new TruckSize(4, 4))
        );

        doThrow(new IOException("Test IOException")).when(objectMapper).writeValue(any(File.class), eq(trucks));

        reportTruckService.saveTrucksToFile(fileName, trucks);

        verify(objectMapper).writeValue(any(File.class), eq(trucks));
        verify(logger).error(eq("Test IOException"), any(IOException.class));
    }
}