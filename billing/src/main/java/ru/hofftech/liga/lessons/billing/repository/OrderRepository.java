package ru.hofftech.liga.lessons.billing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hofftech.liga.lessons.billing.model.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    Page<OrderEntity> findByName(String name, Pageable page);
}
