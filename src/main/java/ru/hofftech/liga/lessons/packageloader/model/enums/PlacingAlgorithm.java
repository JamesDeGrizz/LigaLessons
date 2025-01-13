package ru.hofftech.liga.lessons.packageloader.model.enums;

/**
 * Список доступных алгоритмов размещения посылок в грузовиках
 */
public enum PlacingAlgorithm {
    /**
     * 1 посылка -> 1 грузовик
     */
    OnePerTruck,

    /**
     * Максимальная загрузка грузовика
     */
    FillTruck,

    /**
     * Сбалансированная загрузка доступных грузовиков
     */
    Balanced,

    /**
     * Заглушка для некорректного ввода
     */
    NoneOf;

    private static final int ONE_PER_TRUCK = 0;
    private static final int FILL_TRUCK = 1;
    private static final int BALANCED = 2;

    /**
     * Приведение числового значения к {@code PlacingAlgorithm}
     * @param algorithm - заданный алгоритм
     * @return enum значение. При неправильном вводе возвращается NoneOf
     */
    public static PlacingAlgorithm valueOf(int algorithm) {
        return switch(algorithm) {
            case ONE_PER_TRUCK -> PlacingAlgorithm.OnePerTruck;
            case FILL_TRUCK -> PlacingAlgorithm.FillTruck;
            case BALANCED -> PlacingAlgorithm.Balanced;
            default -> PlacingAlgorithm.NoneOf;
        };
    }
}
