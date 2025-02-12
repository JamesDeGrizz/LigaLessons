package ru.hofftech.liga.lessons.consoleclient.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import ru.hofftech.liga.lessons.consoleclient.model.dto.LoadParcelsResponseDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.LoadParcelsRequestDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.ParcelDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.UnloadTrucksResponseDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.UnloadTrucksRequestDto;

import java.util.List;

public interface ParcelLoaderClient {
    @GetExchange("/api/v1/parcels/{parcelName}")
    ParcelDto getParcel(@PathVariable @NotBlank String parcelName);

    @GetExchange("/api/v1/parcels")
    List<ParcelDto> getAllParcels(
            @RequestParam @Min(0) int offset,
            @RequestParam @Min(1) @Max(1000) int limit);

    @PostExchange("/api/v1/parcels")
    ParcelDto createParcel(@RequestBody @Validated ParcelDto dto);

    @PutExchange("/api/v1/parcels/{targetParcelName}")
    void updateParcel(@PathVariable @NotBlank String targetParcelName,
                        @RequestBody @Validated ParcelDto dto);

    @DeleteExchange("/api/v1/parcels/{parcelName}")
    void deleteParcel(@PathVariable String parcelName);



    @PostExchange("/api/v1/logistic/loading")
    LoadParcelsResponseDto load(@RequestBody LoadParcelsRequestDto dto);

    @PostExchange("/api/v1/logistic/unloading")
    UnloadTrucksResponseDto unload(@RequestBody UnloadTrucksRequestDto dto);
}
