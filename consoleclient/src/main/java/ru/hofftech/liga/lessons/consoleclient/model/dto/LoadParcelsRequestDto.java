package ru.hofftech.liga.lessons.consoleclient.model.dto;

public record LoadParcelsRequestDto(String out,
                                    String type,
                                    String trucks,
                                    String userId,
                                    String outFilename,
                                    String parcelsText,
                                    String parcelsFile) {
}
