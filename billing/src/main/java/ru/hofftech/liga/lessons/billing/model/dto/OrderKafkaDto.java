package ru.hofftech.liga.lessons.billing.model.dto;

import ru.hofftech.liga.lessons.billing.model.enums.Operation;

import java.util.Date;

public record OrderKafkaDto(String name, Date date, Operation operation, int trucksCount, int parcelsCount, int cellsCount) {
}
