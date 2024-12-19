package ru.hofftech.liga.lessons.packageloader.service;

public class TruckContentServiceFactory {
    public static TruckService getTruckService(int width, int height) {
        return new TruckService(width, height);
    }
}
