package ru.hofftech.liga.lessons.packageloader.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_outbox", schema = "parcelloader")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutbox {
    @EmbeddedId
    private OrderOutboxId id;

    @Column(name = "trucks_count")
    private int trucksCount;

    @Column(name = "parcels_count")
    private int parcelsCount;

    @Column(name = "cells_count")
    private int cellsCount;

    private boolean sent;
}
