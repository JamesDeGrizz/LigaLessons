package ru.hofftech.liga.lessons.telegramclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Представляет размер грузовика, используемого для перевозки посылок.
 * Этот класс определяет ширину и высоту грузовика.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public class TruckSize {
    /**
     * Максимальная ширина грузовика.
     */
    public static final int TRUCK_MAX_WIDTH = 6;

    /**
     * Максимальная высота грузовика.
     */
    public static final int TRUCK_MAX_HEIGHT = 6;

    /**
     * Ширина грузовика.
     */
    private final int width;

    /**
     * Высота грузовика.
     */
    private final int height;

    @Override
    public String toString() {
        return width + "x" + height;
    }
}
