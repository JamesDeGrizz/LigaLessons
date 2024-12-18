package ru.hofftech.liga.lessons.packageloader.service;

public class TruckContentServiceFactory {
    public static TruckContentService getTruckContentService(int width, int height) {
        return new TruckContentService(width, height);
    }
}
