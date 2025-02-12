package ru.hofftech.liga.lessons.parcelloader.model.dto;

public record UnloadTrucksRequestDto(String infile,
                                     String outfile,
                                     String userId,
                                     boolean withCount) {
}
