package ru.hofftech.liga.lessons.telegramclient.model.dto;

public record UnloadTrucksRequestDto(String infile,
                                     String outfile,
                                     String userId,
                                     boolean withCount) {
}
