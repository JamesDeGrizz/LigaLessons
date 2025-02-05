package ru.hofftech.liga.lessons.billing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.hofftech.liga.lessons.billing.config.BillingConfiguration;
import ru.hofftech.liga.lessons.billing.mapper.OrderMapper;
import ru.hofftech.liga.lessons.billing.model.dto.UserOrdersResponseDto;
import ru.hofftech.liga.lessons.billing.model.entity.OrderEntity;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxEntity;
import ru.hofftech.liga.lessons.billing.model.enums.Operation;
import ru.hofftech.liga.lessons.billing.model.dto.OrderKafkaDto;
import ru.hofftech.liga.lessons.billing.repository.OrderInboxRepository;
import ru.hofftech.liga.lessons.billing.repository.OrderRepository;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderInboxRepository orderInboxRepository;

    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    BillingService billingService;

    @Mock
    BillingConfiguration billingConfiguration;

    @Mock
    OrderEntitiesService orderEntitiesService;

    @Test
    public void testSaveOrder_Success() {
        OrderKafkaDto orderDto = new OrderKafkaDto(
                "Order1",
                new Date(),
                Operation.LOAD_PARCELS,
                2,
                5,
                10
        );

        OrderInboxEntity mockInboxEntity = new OrderInboxEntity();

        when(orderInboxRepository.save(any(OrderInboxEntity.class))).thenReturn(mockInboxEntity);
        when(billingConfiguration.load()).thenReturn(10);
        when(billingConfiguration.unload()).thenReturn(20);

        billingService.saveOrder(orderDto);

        verify(orderInboxRepository, times(2)).save(any(OrderInboxEntity.class)); // Два вызова: первый для создания, второй для обновления processed
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void testFindUserOrders_NullCommand_ThrowsException() {
        assertThatThrownBy(() -> billingService.findUserOrders(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("\"Заказы не могут быть показаны: userId = null\"");
    }

    @Test
    public void testFindUserOrders_Success() {
        String command = "Order1";

        var now = new Date();
        List<UserOrdersResponseDto> mockResponses = List.of(
                new UserOrdersResponseDto("Order1", now, "Погрузка", 2, 5, 100)
        );

        List<OrderEntity> mockFoundEntities = List.of(
                new OrderEntity(0L, "Order1", now, "Погрузка", 2, 5, 100)
        );

        var pageable = PageRequest.of(0, 1000);
        var page = new PageImpl<>(mockFoundEntities, pageable, mockResponses.size());
        when(orderRepository.findByName("Order1", pageable)).thenReturn(page);
        when(orderMapper.toFindUserOrdersUserResponseDtoList(anyList())).thenReturn(mockResponses);

        List<UserOrdersResponseDto> result = billingService.findUserOrders(command);

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(result.stream()
                    .findFirst()
                    .get()
                    .name())
                .isEqualTo("Order1");
        verify(orderRepository, times(1)).findByName(eq("Order1"), any());
        verify(orderMapper, times(1)).toFindUserOrdersUserResponseDtoList(anyList());
    }
}