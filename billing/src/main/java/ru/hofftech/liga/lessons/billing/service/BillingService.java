package ru.hofftech.liga.lessons.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.billing.config.BillingConfiguration;
import ru.hofftech.liga.lessons.billing.mapper.OrderMapper;
import ru.hofftech.liga.lessons.billing.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.billing.model.dto.FindUserOrdersUserResponseDto;
import ru.hofftech.liga.lessons.billing.model.entity.OrderEntity;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxEntity;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxId;
import ru.hofftech.liga.lessons.billing.model.enums.Operation;
import ru.hofftech.liga.lessons.billing.model.kafka.OrderDto;
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
    private final BillingConfiguration billingConfiguration;
    private final OrderMapper orderMapper;

    @Transactional
    @Cacheable(value = "caffeine", key = "#command.name()")
    public void saveOrder(OrderDto message) {
        var orderInboxEntity = prepareOrderInboxEntity(message);
        orderInboxRepository.save(orderInboxEntity);

        var orderEntity = prepareOrderEntity(message);
        orderRepository.save(orderEntity);

        // я тут явно что-то не так делаю, объясните, пожалуйста, что именно)
        //
        // 3 февраля Руслан спросил:
        // "как реализовать паттерн transactional inbox? Создать отдельную таблицу inbox, затем консюмером добавлять
        // полученные сообщения и потом в отдельном планировщике в одном потоке читать из таблицы inbox и
        // обрабатывать эти сообщения, исключая по messageId дубли?"
        //
        // Борис ответил Руслану, что "нет, нужно добавлять с консьюмера их разом в обе таблицы и в инбокс и в бизнес в одной транзакции".
        // А я был уверен, что Руслан правильно написал, и совершенно не понимаю как сделать так, как написал Борис.
        // Сделал как понял, теперь хочу объяснений)
        orderInboxEntity.setProcessed(true);
        orderInboxRepository.save(orderInboxEntity);
    }

    @Cacheable(value = "caffeine", key = "#command.name()")
    public List<FindUserOrdersUserResponseDto> findUserOrders(FindUserOrdersUserCommandDto command) {
        if (command == null) {
            throw new IllegalArgumentException("Заказы не могут быть показаны: \nПередан пустой список аргументов");
        }

        var pageNumber = 0;
        var result = new ArrayList<FindUserOrdersUserResponseDto>();
        Page<OrderEntity> page;

        do {
            // я понимаю, что делать пагинацию таким образом не надо, она не для того придумана,
            // но во имя экономии времени не буду протягивать её в контракт
            var pageable = PageRequest.of(pageNumber++, PAGE_SIZE);
            page = orderRepository.findByName(command.name(), pageable);

            result.addAll(orderMapper.toFindUserOrdersUserResponseDtoList(page.stream().toList()));
        } while (page.hasNext());

        return result;
    }

    private OrderEntity prepareOrderEntity(OrderDto message) {
        int pricePerCell;
        String operation;
        switch (message.operation()) {
            case Operation.LOAD_PARCELS:
                pricePerCell = billingConfiguration.pricing().load();
                operation = "Погрузка";
                break;
            case Operation.UNLOAD_TRUCKS:
                pricePerCell = billingConfiguration.pricing().unload();
                operation = "Разгрузка";
                break;
            default:
                throw new UnsupportedOperationException("Команда " + message.operation() + " бесплатна");
        }

        var totalPrice = message.cellsCount() * pricePerCell;
        return OrderEntity.builder()
                .name(message.name())
                .date(message.date())
                .operation(operation)
                .trucksCount(message.trucksCount())
                .parcelsCount(message.parcelsCount())
                .totalPrice(totalPrice)
                .build();
    }

    private OrderInboxEntity prepareOrderInboxEntity(OrderDto message) {
        String operation;
        switch (message.operation()) {
            case Operation.LOAD_PARCELS:
                operation = "Погрузка";
                break;
            case Operation.UNLOAD_TRUCKS:
                operation = "Разгрузка";
                break;
            default:
                throw new UnsupportedOperationException("Команда " + message.operation() + " бесплатна");
        }

        return OrderInboxEntity.builder()
                .id(OrderInboxId.builder()
                        .name(message.name())
                        .date(message.date())
                        .operation(operation)
                        .build())
                .trucksCount(message.trucksCount())
                .parcelsCount(message.parcelsCount())
                .cellsCount(message.cellsCount())
                .processed(false)
                .build();
    }
}
