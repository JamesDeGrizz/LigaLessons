package ru.hofftech.liga.lessons.consoleclient.model.dto;

public record FindUserOrdersUserCommandDto(String userId) implements BaseUserCommandDto {
}
