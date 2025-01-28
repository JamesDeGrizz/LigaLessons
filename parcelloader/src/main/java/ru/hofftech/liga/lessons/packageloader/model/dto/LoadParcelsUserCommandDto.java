package ru.hofftech.liga.lessons.packageloader.model.dto;

public record LoadParcelsUserCommandDto(String out,
                                        String type,
                                        String trucks,
                                        String userId,
                                        String outFilename,
                                        String parcelsText,
                                        String parcelsFile) {
}
