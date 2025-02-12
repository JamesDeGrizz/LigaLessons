package ru.hofftech.liga.lessons.parcelloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hofftech.liga.lessons.parcelloader.model.entity.OrderOutbox;
import ru.hofftech.liga.lessons.parcelloader.model.entity.OrderOutboxId;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderOutbox, OrderOutboxId> {
    List<OrderOutbox> findAllBySent(boolean sent);
}