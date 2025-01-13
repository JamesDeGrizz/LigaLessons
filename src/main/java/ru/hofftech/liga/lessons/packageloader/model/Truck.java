package ru.hofftech.liga.lessons.packageloader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Truck {
    private final TruckSize size;
    private final char[][] content;
    private final List<Package> packages = new ArrayList<>();

    public Truck(TruckSize size) {
        this.size = size;
        this.content = new char[size.getWidth()][size.getHeight()];

        IntStream.range(0, size.getWidth())
                .forEach(i -> IntStream.range(0, size.getHeight())
                        .forEach(j -> content[i][j] = ' '));
    }

    public Truck(char[][] content) {
        this.size = new TruckSize(content.length, content[0].length);
        this.content = content;
    }
}
