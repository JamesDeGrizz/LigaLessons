package ru.hofftech.liga.lessons.billing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hofftech.liga.lessons.billing.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findByName(String name, Pageable page);
}
