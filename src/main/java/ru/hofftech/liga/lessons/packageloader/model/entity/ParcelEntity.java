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

@Entity
@Table(name = "parcel", schema = "parcelloader")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ParcelEntity {
    @Id
    private String name;

    @Column(name = "content")
    private String contentRawString;
}
