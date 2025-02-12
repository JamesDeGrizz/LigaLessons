package ru.hofftech.liga.lessons.parcelloader.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReportTruckServiceTest {

    @InjectMocks
    private ReportTruckService reportTruckService;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void testReportTruckContent() throws JsonProcessingException {
        char[][] content = {
                {'A', 'B', 'C'},
                {'D', 'E', 'F'},
                {'G', 'H', 'I'}
        };

        reportTruckService.reportTruckContent(content);

        verify(objectMapper, times(1)).writeValueAsString(any());
    }
}