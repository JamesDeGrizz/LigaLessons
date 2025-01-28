package ru.hofftech.liga.lessons.consoleclient.model.dto;

public record EditParcelUserCommandDto(String currentParcelId, String newParcelId, String form, String symbol) implements BaseUserCommandDto {
}
