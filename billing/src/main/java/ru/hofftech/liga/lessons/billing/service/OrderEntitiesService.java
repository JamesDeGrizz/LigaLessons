package ru.hofftech.liga.lessons.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.billing.config.BillingConfiguration;
import ru.hofftech.liga.lessons.billing.model.Constants;
import ru.hofftech.liga.lessons.billing.model.dto.OrderKafkaDto;
import ru.hofftech.liga.lessons.billing.model.entity.OrderEntity;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxEntity;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxId;
import ru.hofftech.liga.lessons.billing.model.enums.Operation;

@Service
@RequiredArgsConstructor
public class OrderEntitiesService {
    private final BillingConfiguration billingConfiguration;

    public OrderEntity prepareOrderEntity(OrderKafkaDto message) {
        int pricePerCell;
        String operation = switch (message.operation()) {
            case Operation.LOAD_PARCELS -> {
                pricePerCell = billingConfiguration.load();
                yield Constants.LOAD_OPERATION;
            }
            case Operation.UNLOAD_TRUCKS -> {
                pricePerCell = billingConfiguration.unload();
                yield Constants.UNLOAD_OPERATION;
            }
            default -> throw new UnsupportedOperationException("Команда " + message.operation() + " бесплатна");
        };

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

    public OrderInboxEntity prepareOrderInboxEntity(OrderKafkaDto message) {
        String operation = switch (message.operation()) {
            case Operation.LOAD_PARCELS -> Constants.LOAD_OPERATION;
            case Operation.UNLOAD_TRUCKS -> Constants.UNLOAD_OPERATION;
            default -> throw new UnsupportedOperationException("Команда " + message.operation() + " бесплатна");
        };

        return OrderInboxEntity.builder()
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
