package ru.hofftech.liga.lessons.packageloader.service.factory;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.packageloader.service.logistic.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.LogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.OnePerTruckLogisticService;

@AllArgsConstructor
public class LogisticServiceFactory {
    private final TruckServiceFactory truckServiceFactory;

    public LogisticService getLogisticService(PlacingAlgorithm algorithm) {
        return switch (algorithm) {
            case OnePerTruck -> new OnePerTruckLogisticService(truckServiceFactory);
            case FillTruck -> new FullFillTruckLogisticService(truckServiceFactory);
            case Balanced -> new BalancedFillTruckLogisticService(truckServiceFactory);
        };
    }
}
