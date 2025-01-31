package ru.hofftech.liga.lessons.consoleclient.model.dto;

public record FindParcelUserCommandDto(String parcelId) implements BaseUserCommandDto {
}
