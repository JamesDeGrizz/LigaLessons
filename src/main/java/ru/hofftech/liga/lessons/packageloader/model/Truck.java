package ru.hofftech.liga.lessons.packageloader.model;

import lombok.Getter;

@Getter
public class Truck {
    private final char[][] content;

    public Truck(int width, int height) {
        this.content = new char[width][height];
    }
}
