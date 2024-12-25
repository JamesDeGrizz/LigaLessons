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
            case 0 -> PlacingAlgorithm.OnePerTruck;
            case 1 -> PlacingAlgorithm.FillTruck;
            case 2 -> PlacingAlgorithm.Balanced;
            default -> PlacingAlgorithm.NoneOf;
        };
    }
}
