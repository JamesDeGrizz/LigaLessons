package ru.hofftech.liga.lessons.parcelloader.model.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record OrderDto(String name, Date date, String operation, int trucksCount, int parcelsCount, int cellsCount) {
}