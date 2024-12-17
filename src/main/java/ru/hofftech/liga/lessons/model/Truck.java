package ru.hofftech.liga.lessons.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class Truck {
    private final int WIDTH = 6;
    private final int HEIGHT = 6;

    private HashMap<Integer, String> packages;

    public Truck(HashMap<Integer, String> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        for (int i = HEIGHT - 1; i >= 0; i--) {
            stringBuilder.append("+");
            stringBuilder.append(packages.getOrDefault(i, ""));
            stringBuilder.append(" ".repeat(WIDTH - packages.getOrDefault(i, "").length()));
            stringBuilder.append("+\n");
        }
        stringBuilder.append("++++++++\n\n");

        return stringBuilder.toString();
    }
}
