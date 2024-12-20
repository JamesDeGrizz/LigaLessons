package ru.hofftech.liga.lessons.packageloader.service.factory;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.packageloader.service.FillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.LogisticService;
import ru.hofftech.liga.lessons.packageloader.service.OnePerTruckLogisticService;

@AllArgsConstructor
public class LogisticServiceFactory {
    private final TruckServiceFactory truckServiceFactory;

    public LogisticService getLogisticService(PlacingAlgorithm algorithm) {
        return switch (algorithm) {
            case OnePerTruck -> new OnePerTruckLogisticService(truckServiceFactory);
            case FillTruck -> new FillTruckLogisticService(truckServiceFactory);
        };
    }
}
