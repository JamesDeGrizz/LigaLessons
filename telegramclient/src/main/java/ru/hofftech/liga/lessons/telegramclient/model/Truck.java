package ru.hofftech.liga.lessons.telegramclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Представляет грузовик с указанием его размера, содержимого и списка посылок.
 * Этот класс используется для управления грузовиками и их содержимым.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Truck {
    /**
     * Размер грузовика.
     */
    private final TruckSize size;

    /**
     * Содержимое грузовика, представленное в виде двумерного массива символов.
     */
    private final char[][] content;

    /**
     * Список посылок, находящихся в грузовике.
     */
    private final List<Parcel> parcels = new ArrayList<>();

    /**
     * Создает новый грузовик с указанным размером.
     * Содержимое грузовика инициализируется пробелами.
     *
     * @param size размер грузовика
     */
    public Truck(TruckSize size) {
        this.size = size;
        this.content = new char[size.getWidth()][size.getHeight()];

        IntStream.range(0, size.getWidth())
                .forEach(i -> IntStream.range(0, size.getHeight())
                        .forEach(j -> content[i][j] = ' '));
    }

    /**
     * Создает новый грузовик с указанным содержимым.
     * Размер грузовика определяется на основе размеров содержимого.
     *
     * @param content содержимое грузовика
     */
    public Truck(char[][] content) {
        this.size = new TruckSize(content.length, content[0].length);
        this.content = content;
    }
}
