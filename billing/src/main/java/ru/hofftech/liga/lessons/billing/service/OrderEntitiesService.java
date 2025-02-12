package ru.hofftech.liga.lessons.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.billing.config.BillingConfiguration;
import ru.hofftech.liga.lessons.billing.model.Operations;
import ru.hofftech.liga.lessons.billing.model.dto.OrderKafkaDto;
import ru.hofftech.liga.lessons.billing.model.entity.Order;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInbox;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxId;
import ru.hofftech.liga.lessons.billing.model.enums.Operation;

@Service
@RequiredArgsConstructor
public class OrderEntitiesService {
    private final BillingConfiguration billingConfiguration;

    public Order prepareOrderEntity(OrderKafkaDto message) {
        int pricePerCell;
        String operation = switch (message.operation()) {
            case Operation.LOAD_PARCELS -> {
                pricePerCell = billingConfiguration.load();
                yield Operations.LOAD;
            }
            case Operation.UNLOAD_TRUCKS -> {
                pricePerCell = billingConfiguration.unload();
                yield Operations.UNLOAD;
            }
        };

        var totalPrice = message.cellsCount() * pricePerCell;
        return Order.builder()
                .name(message.name())
                .date(message.date())
                .operation(operation)
                .trucksCount(message.trucksCount())
                .parcelsCount(message.parcelsCount())
                .totalPrice(totalPrice)
                .build();
    }

    public OrderInbox prepareOrderInboxEntity(OrderKafkaDto message) {
        String operation = switch (message.operation()) {
            case Operation.LOAD_PARCELS -> Operations.LOAD;
            case Operation.UNLOAD_TRUCKS -> Operations.UNLOAD;
        };

        return OrderInbox.builder()
                .id(OrderInboxId.builder()
                        .name(message.name())
                        .date(message.date())
                        .operation(operation)
                        .build())
                .trucksCount(message.trucksCount())
                .parcelsCount(message.parcelsCount())
                .cellsCount(message.cellsCount())
                .build();
    }
}
