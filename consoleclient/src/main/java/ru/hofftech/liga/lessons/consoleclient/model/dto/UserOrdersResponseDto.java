package ru.hofftech.liga.lessons.consoleclient.model.dto;

import java.util.Date;

public record UserOrdersResponseDto(
        String name,
        Date date,
        String operation,
        int trucksCount,
        int parcelsCount,
        int totalPrice) {

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Дата ")
                .append(date)
                .append("; операция ")
                .append(operation)
                .append("; грузовиков ")
                .append(trucksCount)
                .append("; посылок ")
                .append(parcelsCount)
                .append("; итого ")
                .append(totalPrice)
                .toString();
    }
}
