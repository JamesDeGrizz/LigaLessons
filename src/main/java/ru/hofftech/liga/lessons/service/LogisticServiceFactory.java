package ru.hofftech.liga.lessons.service;

import ru.hofftech.liga.lessons.model.PlacingAlgorithm;

public class LogisticServiceFactory {
    public static LogisticService getLogisticService(PlacingAlgorithm algorithm) {
        return switch (algorithm) {
            case OnePerTruck -> new OnePerTruckLogisticService();
            case FillTruck -> new FillTruckLogisticService();
            default -> throw new IllegalArgumentException("Неизвестный алгоритм размещения: " + algorithm);
        };
    }
}
