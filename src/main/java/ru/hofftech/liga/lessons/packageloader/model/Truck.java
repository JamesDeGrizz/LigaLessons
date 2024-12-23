package ru.hofftech.liga.lessons.packageloader.model;

import lombok.Getter;

import java.util.stream.IntStream;

@Getter
public class Truck {
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
