package ru.hofftech.liga.lessons.billing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import ru.hofftech.liga.lessons.billing.mapper.OrderMapper;
import ru.hofftech.liga.lessons.billing.model.dto.OrderKafkaDto;
import ru.hofftech.liga.lessons.billing.model.dto.UserOrdersResponseDto;
import ru.hofftech.liga.lessons.billing.model.entity.Order;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInbox;
import ru.hofftech.liga.lessons.billing.model.enums.Operation;
import ru.hofftech.liga.lessons.billing.repository.OrderInboxRepository;
import ru.hofftech.liga.lessons.billing.repository.OrderRepository;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
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

        OrderInbox mockInboxEntity = new OrderInbox();
        Order mockEntity = new Order();

        when(orderEntitiesService.prepareOrderEntity(any(OrderKafkaDto.class))).thenReturn(mockEntity);
        when(orderEntitiesService.prepareOrderInboxEntity(any(OrderKafkaDto.class))).thenReturn(mockInboxEntity);
        when(orderInboxRepository.save(any(OrderInbox.class))).thenReturn(mockInboxEntity);

        billingService.saveOrder(orderDto);

        verify(orderInboxRepository, times(1)).save(any(OrderInbox.class)); // Два вызова: первый для создания, второй для обновления processed
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testFindUserOrders_NullCommand_ThrowsException() {
        assertThatThrownBy(() -> billingService.findUserOrders(null, 0, 100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Заказы не могут быть показаны: userId = null");
    }

    @Test
    public void testFindUserOrders_Success() {
        String command = "Order1";

        var now = new Date();
        List<UserOrdersResponseDto> mockResponses = List.of(
                new UserOrdersResponseDto("Order1", now, "Погрузка", 2, 5, 100)
        );

        List<Order> mockFoundEntities = List.of(
                new Order(0L, "Order1", now, "Погрузка", 2, 5, 100)
        );

        var pageable = PageRequest.of(0, 100);
        var page = new PageImpl<>(mockFoundEntities, pageable, mockResponses.size());
        when(orderRepository.findByName("Order1", pageable)).thenReturn(page);
        when(orderMapper.toUserOrdersResponseDtoList(anyList())).thenReturn(mockResponses);

        List<UserOrdersResponseDto> result = billingService.findUserOrders(command, 0, 100);

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
        verify(orderMapper, times(1)).toUserOrdersResponseDtoList(anyList());
    }
}