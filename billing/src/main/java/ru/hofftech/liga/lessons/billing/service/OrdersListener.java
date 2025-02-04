package ru.hofftech.liga.lessons.billing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.billing.model.kafka.OrderDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersListener {
    private final BillingService billingService;

    public void process(OrderDto message) {
        billingService.saveOrder(message);
    }
}
