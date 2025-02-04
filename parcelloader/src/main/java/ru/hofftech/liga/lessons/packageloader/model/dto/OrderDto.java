package ru.hofftech.liga.lessons.packageloader.model.dto;

import ru.hofftech.liga.lessons.packageloader.model.enums.Command;

import java.util.Date;

public record OrderDto(String name, Date date, Command operation, int trucksCount, int parcelsCount, int cellsCount) {
}