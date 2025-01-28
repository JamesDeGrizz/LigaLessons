package ru.hofftech.liga.lessons.consoleclient.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import ru.hofftech.liga.lessons.consoleclient.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.UnloadTrucksUserCommandDto;

public interface ParcelLoaderClientService {
    @GetExchange("/api/v1/parcels/{parcelId}")
    String getParcel(@PathVariable String parcelId);

    @PostExchange("/api/v1/parcels")
    String createParcel(@RequestBody CreateParcelUserCommandDto dto);

    @PutExchange("/api/v1/parcels/{parcelId}")
    String updateParcel(@PathVariable String parcelId,
                        @RequestBody CreateParcelUserCommandDto dto);

    @DeleteExchange("/api/v1/parcels/{parcelId}")
    String deleteParcel(@PathVariable String parcelId);

    @PostExchange("/api/v1/logistic/load")
    String load(@RequestBody LoadParcelsUserCommandDto dto);

    @PostExchange("/api/v1/logistic/unload")
    String unload(@RequestBody UnloadTrucksUserCommandDto dto);

    @GetExchange("/api/v1/orders/{userid}")
    String findUserOrders(@PathVariable String userid);
}
