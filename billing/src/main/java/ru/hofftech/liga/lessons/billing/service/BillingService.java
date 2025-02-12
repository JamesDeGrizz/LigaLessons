package ru.hofftech.liga.lessons.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.billing.mapper.OrderMapper;
import ru.hofftech.liga.lessons.billing.model.dto.OrderKafkaDto;
import ru.hofftech.liga.lessons.billing.model.dto.UserOrdersResponseDto;
import ru.hofftech.liga.lessons.billing.repository.OrderInboxRepository;
import ru.hofftech.liga.lessons.billing.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BillingService {
    private final OrderRepository orderRepository;
    private final OrderInboxRepository orderInboxRepository;
    private final OrderEntitiesService orderEntitiesService;
    private final OrderMapper orderMapper;

    @Transactional
    @Cacheable(value = "caffeine", key = "#message.name()")
    public void saveOrder(OrderKafkaDto message) {
        var orderInboxEntity = orderEntitiesService.prepareOrderInboxEntity(message);
        orderInboxRepository.save(orderInboxEntity);

        var orderEntity = orderEntitiesService.prepareOrderEntity(message);
        orderRepository.save(orderEntity);
    }

    @Cacheable(value = "caffeine", key = "#userId")
    public List<UserOrdersResponseDto> findUserOrders(String userId, int offset, int limit) {
        if (userId == null) {
            throw new IllegalArgumentException("Заказы не могут быть показаны: userId = null");
        }

        var pageNumber = offset / limit;
        var pageable = PageRequest.of(pageNumber, limit);
        var page = orderRepository.findByName(userId, pageable);

        return new ArrayList<>(
                orderMapper.toUserOrdersResponseDtoList(page.stream().toList()));
    }
}
