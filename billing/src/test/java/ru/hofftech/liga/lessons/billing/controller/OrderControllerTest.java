package ru.hofftech.liga.lessons.billing.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.hofftech.liga.lessons.billing.model.dto.UserOrdersResponseDto;
import ru.hofftech.liga.lessons.billing.service.BillingService;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BillingService billingService;

    @BeforeEach
    void init(WebApplicationContext webApplicationContext) {
        if (mockMvc == null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }
    }

    @Test
    public void testFindUserOrders_Success() throws Exception {
        String userId = "user123";
        List<UserOrdersResponseDto> mockResponses = List.of(
                new UserOrdersResponseDto("Order1", new Date(), "Погрузка", 2, 5, 100)
        );

        when(billingService.findUserOrders(userId, 0, 100))
                .thenReturn(mockResponses);

        var uriTemplate = new StringBuilder("/api/v1/orders/")
                .append(userId)
                .append("?offset=")
                .append(0)
                .append("&limit=")
                .append(100)
                .toString();
        mockMvc.perform(get(uriTemplate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Order1"))
                .andExpect(jsonPath("$[0].operation").value("Погрузка"));

        verify(billingService, times(1)).findUserOrders(userId, 0, 100);
    }
}