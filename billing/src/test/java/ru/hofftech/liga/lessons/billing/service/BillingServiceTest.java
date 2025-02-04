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
import ru.hofftech.liga.lessons.billing.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.billing.model.dto.FindUserOrdersUserResponseDto;
import ru.hofftech.liga.lessons.billing.model.entity.OrderEntity;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxEntity;
import ru.hofftech.liga.lessons.billing.model.enums.Operation;
import ru.hofftech.liga.lessons.billing.model.kafka.OrderDto;
import ru.hofftech.liga.lessons.billing.repository.OrderInboxRepository;
import ru.hofftech.liga.lessons.billing.repository.OrderRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void testSaveOrder_Success() {
        // Arrange
        OrderDto orderDto = new OrderDto(
                "Order1",
                new Date(),
                Operation.LOAD_PARCELS,
                2,
                5,
                10
        );

        OrderInboxEntity mockInboxEntity = new OrderInboxEntity();
        mockInboxEntity.setProcessed(false);

        when(orderInboxRepository.save(any(OrderInboxEntity.class))).thenReturn(mockInboxEntity);
        when(billingConfiguration.pricing()).thenReturn(new BillingConfiguration.Pricing(10, 20));
//        doNothing().when(orderRepository).save(any(OrderEntity.class));

        // Act
        billingService.saveOrder(orderDto);

        // Assert
        verify(orderInboxRepository, times(2)).save(any(OrderInboxEntity.class)); // Два вызова: первый для создания, второй для обновления processed
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void testFindUserOrders_NullCommand_ThrowsException() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billingService.findUserOrders(null);
        });
        assertEquals("Заказы не могут быть показаны: \nПередан пустой список аргументов", exception.getMessage());
    }

    @Test
    public void testFindUserOrders_Success() {
        // Arrange
        FindUserOrdersUserCommandDto command = new FindUserOrdersUserCommandDto("Order1");

        var now = new Date();
        List<FindUserOrdersUserResponseDto> mockResponses = List.of(
                new FindUserOrdersUserResponseDto("Order1", now, "Погрузка", 2, 5, 100)
        );

        List<OrderEntity> mockFoundEntities = List.of(
                new OrderEntity(0L, "Order1", now, "Погрузка", 2, 5, 100)
        );

        var pageable = PageRequest.of(0, 1000);
        var page = new PageImpl<OrderEntity>(mockFoundEntities, pageable, mockResponses.size());
        when(orderRepository.findByName("Order1", pageable)).thenReturn(page);
        when(orderMapper.toFindUserOrdersUserResponseDtoList(anyList())).thenReturn(mockResponses);

        // Act
        List<FindUserOrdersUserResponseDto> result = billingService.findUserOrders(command);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Order1", result.get(0).name());
        verify(orderRepository, times(1)).findByName(eq("Order1"), any());
        verify(orderMapper, times(1)).toFindUserOrdersUserResponseDtoList(anyList());
    }
}