package ru.hofftech.liga.lessons.packageloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hofftech.liga.lessons.packageloader.model.entity.ParcelEntity;

import java.util.Optional;

public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {
    Optional<ParcelEntity> findByName(String name);
}
