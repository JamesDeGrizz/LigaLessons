package ru.hofftech.liga.lessons.packageloader.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FindParcelUserCommandResponseDto(List<FindParcelUserCommandResponseListItemDto> parcels) {

    @Builder
    public record FindParcelUserCommandResponseListItemDto(String parcelId, String form, String symbol) {
    }
}