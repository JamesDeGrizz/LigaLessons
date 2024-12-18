package ru.hofftech.liga.lessons.model;

public class Truck {
    private TruckContent content;

    public Truck(TruckContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
