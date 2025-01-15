package ru.hofftech.liga.lessons.packageloader.service.factory;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.LogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.packageloader.service.logistic.OnePerTruckLogisticService;

@AllArgsConstructor
public class LogisticServiceFactory {
    private final TruckService truckService;

    public LogisticService getLogisticService(PlacingAlgorithm algorithm) {
        return switch (algorithm) {
            case ONE_PER_TRUCK -> new OnePerTruckLogisticService(truckService);
            case FILL_TRUCK -> new FullFillTruckLogisticService(truckService);
            case BALANCED -> new BalancedFillTruckLogisticService(truckService);
            case NONE_OF -> throw new IllegalArgumentException("Не выбран ни один алгоритм");
        };
    }
}
