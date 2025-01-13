package ru.hofftech.liga.lessons.packageloader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public class TruckSize {
    public static final int TRUCK_MAX_WIDTH = 6;
    public static final int TRUCK_MAX_HEIGHT = 6;

    private final int width;
    private final int height;
}
