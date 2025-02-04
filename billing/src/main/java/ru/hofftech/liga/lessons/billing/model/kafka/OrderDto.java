package ru.hofftech.liga.lessons.billing.model.kafka;

import ru.hofftech.liga.lessons.billing.model.enums.Operation;

import java.util.Date;

public record OrderDto(String name, Date date, Operation operation, int trucksCount, int parcelsCount, int cellsCount) {
}
