package ru.hofftech.liga.lessons.consoleclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Представляет точку размещения с указанием высоты и ширины.
 * Этот класс используется для определения координат размещения пакетов.
 */
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class PlacingPoint {
    /**
     * Высота точки размещения.
     */
    private final int height;

    /**
     * Ширина точки размещения.
     */
    private final int width;
}
