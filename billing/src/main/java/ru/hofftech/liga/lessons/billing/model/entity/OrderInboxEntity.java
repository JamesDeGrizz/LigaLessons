package ru.hofftech.liga.lessons.billing.model.entity;

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
@Table(name = "user_order_inbox", schema = "billing")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInboxEntity {
    @EmbeddedId
    private OrderInboxId id;

    @Column(name = "trucks_count")
    private int trucksCount;

    @Column(name = "parcels_count")
    private int parcelsCount;

    @Column(name = "cells_count")
    private int cellsCount;

    private boolean processed;
}
