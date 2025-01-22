package ru.hofftech.liga.lessons.packageloader.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
public class OrderRepository {
    private List<Order> orders = new ArrayList<>();

    public void saveOrder(Order order) {
        if (order == null) {
            log.error("Заказ равен null");
            return;
        }

        orders.add(order);
    }

    public List<Order> findOrdersByUserId(String userId) {
        return orders.stream()
                .filter(order -> order.userId().equals(userId))
                .collect(Collectors.toList());
    }
}
