package ru.hofftech.liga.lessons.packageloader.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Package {
    private final List<String> content;
    private final String name;
    private final char symbol;

    private List<PlacingPoint> placingPoints = null;

    public char charAt(int row, int column) {
        return content.get(row).charAt(column);
    }

    public int getWidth() {
        if (content.isEmpty()) {
            return 0;
        }
        return content.stream().
                max(Comparator.comparing(String::length))
                .get()
                .length();
    }

    public int getHeight() {
        return content.size();
    }

    public boolean placed() {
        return placingPoints != null;
    }

    public void placePackage(List<PlacingPoint> placingPoints) {
        this.placingPoints = placingPoints;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("\r\n");
        builder.append("Name: ").append(name).append("\r\n");
        builder.append("Form: ").append("\r\n");
        for (var line : content) {
            builder.append(line.replaceAll("\\S", String.valueOf(symbol))).append("\r\n");
        }
        builder.append("Symbol: ").append(symbol).append("\r\n");
        return builder.toString();
    }
}
