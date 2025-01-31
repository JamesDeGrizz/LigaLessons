package ru.hofftech.liga.lessons.packageloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReportTruckServiceTest {

    @InjectMocks
    private ReportTruckService reportTruckService;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void testReportTruckContent() {
        // Arrange
        char[][] content = {
                {'A', 'B', 'C'},
                {'D', 'E', 'F'},
                {'G', 'H', 'I'}
        };

        // Act
        reportTruckService.reportTruckContent(content);

        // Assert
        // Здесь можно добавить проверку вывода в лог, если это необходимо
    }
}