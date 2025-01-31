package ru.hofftech.liga.lessons.packageloader.model.dto;

public record UnloadTrucksUserCommandDto(String infile,
                                         String outfile,
                                         String userId,
                                         boolean withCount) {
}
