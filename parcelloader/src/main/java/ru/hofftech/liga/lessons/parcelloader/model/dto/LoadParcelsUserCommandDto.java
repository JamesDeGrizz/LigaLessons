package ru.hofftech.liga.lessons.parcelloader.model.dto;

public record LoadParcelsUserCommandDto(String out,
                                        String type,
                                        String trucks,
                                        String userId,
                                        String outFilename,
                                        String parcelsText,
                                        String parcelsFile) {
}
