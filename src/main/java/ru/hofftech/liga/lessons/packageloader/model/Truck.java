package ru.hofftech.liga.lessons.packageloader.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Truck {
    private TruckContent content;

    @Override
    public String toString() {
        return content.toString();
    }
}
