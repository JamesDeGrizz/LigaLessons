package ru.hofftech.liga.lessons.packageloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hofftech.liga.lessons.packageloader.model.entity.ParcelEntity;

import java.util.Optional;

@Repository
public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {
    Optional<ParcelEntity> findByName(String name);
}
