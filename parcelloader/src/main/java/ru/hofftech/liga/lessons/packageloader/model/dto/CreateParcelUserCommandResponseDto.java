package ru.hofftech.liga.lessons.packageloader.model.dto;

import lombok.Builder;

@Builder
public record CreateParcelUserCommandResponseDto(String parcelId, String form, String symbol) {
}
