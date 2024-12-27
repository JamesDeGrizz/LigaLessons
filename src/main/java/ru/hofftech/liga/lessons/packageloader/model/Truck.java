package ru.hofftech.liga.lessons.packageloader.model;

import lombok.Getter;

import java.util.stream.IntStream;

@Getter
public class Truck {
    public static final int TRUCK_MAX_WIDTH = 6;
    public static final int TRUCK_MAX_HEIGHT = 6;
    private final char[][] content;

    public Truck(int width, int height) {
        this.content = new char[width][height];

        IntStream.range(0, height)
                .forEach(i -> IntStream.range(0, width)
                        .forEach(j -> content[i][j] = ' '));
    }

    public Truck(char[][] content) {
        this.content = content;
    }
}
