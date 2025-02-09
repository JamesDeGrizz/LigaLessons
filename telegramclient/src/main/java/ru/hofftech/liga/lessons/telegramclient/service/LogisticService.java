package ru.hofftech.liga.lessons.telegramclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.telegramclient.model.dto.LoadParcelsRequestDto;
import ru.hofftech.liga.lessons.telegramclient.model.dto.UnloadTrucksRequestDto;

@Service
@RequiredArgsConstructor
public class LogisticService {
    private final ParcelLoaderClient parcelLoaderClientService;

    public String load(LoadParcelsRequestDto dto) {
        return parcelLoaderClientService.load(dto).logs();
    }

    public String unload(UnloadTrucksRequestDto dto) {
        return parcelLoaderClientService.unload(dto).logs();
    }
}
