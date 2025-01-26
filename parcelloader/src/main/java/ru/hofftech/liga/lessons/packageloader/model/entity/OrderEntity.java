package ru.hofftech.liga.lessons.packageloader.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "user_order", schema = "parcelloader")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @Column(name = "id")
    private String userId;

    private Date date;

    private String operation;

    @Column(name = "trucks_count")
    private int trucksCount;

    @Column(name = "parcels_count")
    private int parcelsCount;

    @Column(name = "total_price")
    private int totalPrice;
}
