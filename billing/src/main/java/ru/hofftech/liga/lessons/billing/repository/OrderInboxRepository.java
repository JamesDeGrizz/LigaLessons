package ru.hofftech.liga.lessons.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxEntity;
import ru.hofftech.liga.lessons.billing.model.entity.OrderInboxId;

@Repository
public interface OrderInboxRepository extends JpaRepository<OrderInboxEntity, OrderInboxId> {
}
