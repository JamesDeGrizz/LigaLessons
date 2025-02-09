package ru.hofftech.liga.lessons.telegramclient.model.dto;

public record LoadParcelsRequestDto(String out,
                                    String type,
                                    String trucks,
                                    String userId,
                                    String outFilename,
                                    String parcelsText,
                                    String parcelsFile) {
}
