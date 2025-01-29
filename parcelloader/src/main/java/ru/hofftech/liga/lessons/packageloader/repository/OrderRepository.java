package ru.hofftech.liga.lessons.packageloader.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hofftech.liga.lessons.packageloader.model.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    Page<OrderEntity> findByUserId(String userId, Pageable page);
}
