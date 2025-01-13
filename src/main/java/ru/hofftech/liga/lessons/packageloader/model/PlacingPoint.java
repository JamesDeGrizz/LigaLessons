package ru.hofftech.liga.lessons.packageloader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class PlacingPoint {
    private final int height;
    private final int width;
}
