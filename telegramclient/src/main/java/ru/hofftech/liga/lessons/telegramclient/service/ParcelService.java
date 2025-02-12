package ru.hofftech.liga.lessons.telegramclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.telegramclient.model.dto.ParcelDto;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParcelService {
    private final ParcelLoaderClient parcelLoaderClientService;

    public String createParcel(ParcelDto parcel) {
        parcelLoaderClientService.createParcel(parcel);
        return "Посылка успешно создана";
    }

    public String findParcel(String parcelName) {
        if (parcelName == null || parcelName == "") {
            return parcelLoaderClientService.getAllParcels(0, 100).stream()
                    .map(ParcelDto::toString)
                    .collect(Collectors.joining("\n"));
        }
        return parcelLoaderClientService.getParcel(parcelName).toString();
    }

    public String updateParcel(String targetParcelName, ParcelDto parcel) {
        parcelLoaderClientService.updateParcel(targetParcelName, parcel);
        return "Посылка успешно отредактирована";
    }

    public String deleteParcel(String parcelName) {
        parcelLoaderClientService.deleteParcel(parcelName);
        return "Посылка успешно удалена";
    }
}
