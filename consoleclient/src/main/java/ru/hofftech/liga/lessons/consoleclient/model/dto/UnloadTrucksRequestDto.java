package ru.hofftech.liga.lessons.consoleclient.model.dto;

public record UnloadTrucksRequestDto(String infile,
                                     String outfile,
                                     String userId,
                                     boolean withCount) {
}
