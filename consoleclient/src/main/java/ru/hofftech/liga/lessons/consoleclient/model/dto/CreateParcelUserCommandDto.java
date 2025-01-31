package ru.hofftech.liga.lessons.consoleclient.model.dto;

public record CreateParcelUserCommandDto(String parcelId, String form, String symbol) implements BaseUserCommandDto {
}
