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
            case ONE_PER_TRUCK -> new OnePerTruckLogisticService(truckServiceFactory);
            case FILL_TRUCK -> new FullFillTruckLogisticService(truckServiceFactory);
            case BALANCED -> new BalancedFillTruckLogisticService(truckServiceFactory);
            case NONE_OF -> throw new IllegalArgumentException("Не выбран ни один алгоритм");
        };
    }
}
