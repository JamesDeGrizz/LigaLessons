package ru.hofftech.liga.lessons.parcelloader.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hofftech.liga.lessons.parcelloader.service.OrderUnboxService;

/**
 * Класс для обработки необработанных заказов
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {
    private final OrderUnboxService orderUnboxService;

    @Value("${task.send-unprocessed-orders.rate-ms}")
    private final int sendUnprocessedOrdersRateMs = 1000;

    @Scheduled(fixedRate = sendUnprocessedOrdersRateMs)
    @Transactional
    public void processUnprocessedOrders() {
        orderUnboxService.sendUnprocessedOrders();
    }
}
