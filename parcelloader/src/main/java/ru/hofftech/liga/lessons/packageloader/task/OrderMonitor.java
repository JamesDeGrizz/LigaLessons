package ru.hofftech.liga.lessons.packageloader.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hofftech.liga.lessons.packageloader.model.dto.OrderDto;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.packageloader.service.KafkaSenderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMonitor {
    private final OrderRepository orderRepository;
    private final KafkaSenderService kafkaSenderService;

    @Scheduled(fixedRate = 1000)
    public void sendUnprocessedOrders() {
        var unprocessedOrders = orderRepository.findAllBySent(false);

        if (unprocessedOrders.isEmpty()) {
            log.debug("Неотправленных заказов не найдено");
            return;
        }

        log.info("Найдено {} неотправленных заказов", unprocessedOrders.size());

        for (var unprocessedOrder : unprocessedOrders) {
            kafkaSenderService.sendNewOrder(OrderDto.builder()
                            .name(unprocessedOrder.getId().getName())
                    .date(unprocessedOrder.getId().getDate())
                    .operation(unprocessedOrder.getId().getOperation())
                    .trucksCount(unprocessedOrder.getTrucksCount())
                    .parcelsCount(unprocessedOrder.getParcelsCount())
                    .cellsCount(unprocessedOrder.getCellsCount())
                    .build());

            unprocessedOrder.setSent(true);
            orderRepository.save(unprocessedOrder);
        }

        log.info("Заказы успешно обработаны");
    }
}
