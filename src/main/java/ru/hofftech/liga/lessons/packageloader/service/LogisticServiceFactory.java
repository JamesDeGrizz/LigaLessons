package ru.hofftech.liga.lessons.packageloader.service;

import ru.hofftech.liga.lessons.packageloader.model.enums.PlacingAlgorithm;

public class LogisticServiceFactory {
    public static LogisticService getLogisticService(PlacingAlgorithm algorithm) {
        return switch (algorithm) {
            case OnePerTruck -> new OnePerTruckLogisticService();
            case FillTruck -> new FillTruckLogisticService();
        };
    }
}
