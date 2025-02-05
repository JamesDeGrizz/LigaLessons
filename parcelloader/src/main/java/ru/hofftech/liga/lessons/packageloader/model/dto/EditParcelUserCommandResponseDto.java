package ru.hofftech.liga.lessons.packageloader.model.dto;

import lombok.Builder;

@Builder
public record EditParcelUserCommandResponseDto(String newParcelId, String form, String symbol) {
}
