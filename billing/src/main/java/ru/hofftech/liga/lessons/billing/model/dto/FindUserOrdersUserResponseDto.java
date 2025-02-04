package ru.hofftech.liga.lessons.billing.model.dto;

import java.util.Date;

public record FindUserOrdersUserResponseDto(
        String name,
        Date date,
        String operation,
        int trucksCount,
        int parcelsCount,
        int totalPrice) {
}
