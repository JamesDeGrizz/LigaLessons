package ru.hofftech.liga.lessons.parcelloader.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.kafka.KafkaContainer;
import ru.hofftech.liga.lessons.parcelloader.model.dto.ParcelDto;
import ru.hofftech.liga.lessons.parcelloader.service.ParcelService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ParcelControllerTest {
    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer("apache/kafka-native:3.8.0");

    private MockMvc mockMvc;

    @Mock
    private ParcelService parcelService;

    @InjectMocks
    private ParcelController parcelController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        System.setProperty("spring.datasource.url", postgresqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresqlContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresqlContainer.getPassword());
        System.setProperty("spring.kafka.bootstrap-servers", kafkaContainer.getBootstrapServers());
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(parcelController).build();
    }

    @Test
    void findAll_shouldReturnListOfParcels() throws Exception {
        ParcelDto parcelDto = ParcelDto.builder()
                .name("parcel1")
                .form("A,B,C")
                .symbol("X")
                .build();

        when(parcelService.findAll(0, 10)).thenReturn(List.of(parcelDto));

        mockMvc.perform(get("/api/v1/parcels")
                        .param("offset", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("parcel1"))
                .andExpect(jsonPath("$[0].form").value("A,B,C"))
                .andExpect(jsonPath("$[0].symbol").value("X"));

        verify(parcelService, times(1)).findAll(0, 10);
    }

    @Test
    void find_shouldReturnParcelByName() throws Exception {
        ParcelDto parcelDto = ParcelDto.builder()
                .name("parcel1")
                .form("A,B,C")
                .symbol("X")
                .build();

        when(parcelService.find("parcel1")).thenReturn(parcelDto);

        mockMvc.perform(get("/api/v1/parcels/{parcelName}", "parcel1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("parcel1"))
                .andExpect(jsonPath("$.form").value("A,B,C"))
                .andExpect(jsonPath("$.symbol").value("X"));

        verify(parcelService, times(1)).find("parcel1");
    }

    @Test
    void create_shouldCreateNewParcel() throws Exception {
        ParcelDto parcelDto = ParcelDto.builder()
                .name("parcel1")
                .form("A,B,C")
                .symbol("X")
                .build();

        when(parcelService.create(any(ParcelDto.class))).thenReturn(parcelDto);

        mockMvc.perform(post("/api/v1/parcels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcelDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("parcel1"))
                .andExpect(jsonPath("$.form").value("A,B,C"))
                .andExpect(jsonPath("$.symbol").value("X"));

        verify(parcelService, times(1)).create(any(ParcelDto.class));
    }

    @Test
    void update_shouldUpdateExistingParcel() throws Exception {
        ParcelDto parcelDto = ParcelDto.builder()
                .name("updatedParcel")
                .form("D,E,F")
                .symbol("Y")
                .build();

        doNothing().when(parcelService).edit(eq("parcel1"), any(ParcelDto.class));

        mockMvc.perform(put("/api/v1/parcels/{targetParcelName}", "parcel1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcelDto)))
                .andExpect(status().isOk());

        verify(parcelService, times(1)).edit(eq("parcel1"), any(ParcelDto.class));
    }

    @Test
    void delete_shouldDeleteParcelByName() throws Exception {
        doNothing().when(parcelService).delete("parcel1");

        mockMvc.perform(delete("/api/v1/parcels/{parcelName}", "parcel1"))
                .andExpect(status().isOk());

        verify(parcelService, times(1)).delete("parcel1");
    }
}