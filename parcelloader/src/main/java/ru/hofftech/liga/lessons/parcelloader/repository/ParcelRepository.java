package ru.hofftech.liga.lessons.parcelloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hofftech.liga.lessons.parcelloader.model.entity.ParcelEntity;

import java.util.Optional;

public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {
    Optional<ParcelEntity> findByName(String name);
}
