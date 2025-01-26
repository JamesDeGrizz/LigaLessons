package ru.hofftech.liga.lessons.packageloader.model.enums;

/**
 * Список доступных алгоритмов размещения посылок в грузовиках
 */
public enum PlacingAlgorithm {
    /**
     * 1 посылка -> 1 грузовик
     */
    ONE_PER_TRUCK,

    /**
     * Максимальная загрузка грузовика
     */
    FILL_TRUCK,

    /**
     * Сбалансированная загрузка доступных грузовиков
     */
    BALANCED,

    /**
     * Заглушка для некорректного ввода
     */
    NONE_OF;

    private static final int ONE_PER_TRUCK_NUMBER = 0;
    private static final int FILL_TRUCK_NUMBER = 1;
    private static final int BALANCED_NUMBER = 2;

    /**
     * Приведение числового значения к {@code PlacingAlgorithm}
     * @param algorithm - заданный алгоритм
     * @return enum значение. При неправильном вводе возвращается NoneOf
     */
    public static PlacingAlgorithm valueOf(int algorithm) {
        return switch(algorithm) {
            case ONE_PER_TRUCK_NUMBER -> PlacingAlgorithm.ONE_PER_TRUCK;
            case FILL_TRUCK_NUMBER -> PlacingAlgorithm.FILL_TRUCK;
            case BALANCED_NUMBER -> PlacingAlgorithm.BALANCED;
            default -> PlacingAlgorithm.NONE_OF;
        };
    }
}
