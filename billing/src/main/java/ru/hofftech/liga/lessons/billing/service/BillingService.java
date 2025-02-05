package ru.hofftech.liga.lessons.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.billing.mapper.OrderMapper;
import ru.hofftech.liga.lessons.billing.model.dto.UserOrdersResponseDto;
import ru.hofftech.liga.lessons.billing.model.dto.OrderKafkaDto;
import ru.hofftech.liga.lessons.billing.model.entity.OrderEntity;
import ru.hofftech.liga.lessons.billing.repository.OrderInboxRepository;
import ru.hofftech.liga.lessons.billing.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BillingService {
    private static final int PAGE_SIZE = 1000;

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
    public List<UserOrdersResponseDto> findUserOrders(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Заказы не могут быть показаны: userId = null");
        }

        var pageNumber = 0;
        var result = new ArrayList<UserOrdersResponseDto>();
        Page<OrderEntity> page;

        do {
            // я понимаю, что делать пагинацию таким образом не надо, она не для того придумана,
            // но во имя экономии времени не буду протягивать её в контракт
            var pageable = PageRequest.of(pageNumber++, PAGE_SIZE);
            page = orderRepository.findByName(userId, pageable);

            result.addAll(orderMapper.toFindUserOrdersUserResponseDtoList(page.stream().toList()));
        } while (page.hasNext());

        return result;
    }
}
