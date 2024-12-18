package ru.hofftech.liga.lessons.service;

public class TruckContentServiceFactory {
    public static TruckContentService getTruckContentService(int width, int height) {
        return new TruckContentService(width, height);
    }
}
