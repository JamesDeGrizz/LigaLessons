package ru.hofftech.liga.lessons.packageloader.model.enums;

public enum PlacingAlgorithm {
    OnePerTruck,
    FillTruck,
    Balanced,
    NoneOf;

    private static final int ONE_PER_TRUCK = 0;
    private static final int FILL_TRUCK = 1;
    private static final int BALANCED = 2;

    public static PlacingAlgorithm valueOf(int algorithm) {
        return switch(algorithm) {
            case ONE_PER_TRUCK -> PlacingAlgorithm.OnePerTruck;
            case FILL_TRUCK -> PlacingAlgorithm.FillTruck;
            case BALANCED -> PlacingAlgorithm.Balanced;
            default -> PlacingAlgorithm.NoneOf;
        };
    }
}
