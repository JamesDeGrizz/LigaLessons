package ru.hofftech.liga.lessons.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxEntity;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxId;

public interface OrderInboxRepository extends JpaRepository<OrderInboxEntity, OrderInboxId> {
}
