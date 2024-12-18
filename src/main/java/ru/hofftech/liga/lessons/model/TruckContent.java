package ru.hofftech.liga.lessons.model;

import lombok.Getter;

import java.util.stream.IntStream;

@Getter
public class TruckContent {
    private char[][] content;

    public TruckContent(int width, int height) {
        this.content = new char[width][height];
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("\n");

        IntStream.range(0, content.length).mapToObj(i -> content[content.length - 1 - i]).forEach(row -> {
            builder.append("+");
            IntStream.range(0, row.length).forEach(j -> builder.append(row[j]));
            builder.append("+\n");
        });

        builder.append("+");
        IntStream.range(0, content[0].length).forEach(j -> builder.append("+"));
        builder.append("+\n");

        return builder.toString();
    }
}
