package ru.hofftech.liga.lessons.consoleclient.model.dto;

public record UnloadTrucksUserCommandDto(String infile,
                                         String outfile,
                                         String userId,
                                         boolean withCount) implements BaseUserCommandDto {
}
