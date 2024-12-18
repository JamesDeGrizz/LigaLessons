package ru.hofftech.liga.lessons.packageloader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@AllArgsConstructor
public class Package {
    private List<String> content;

    public char charAt(int row, int column) {
        return content.get(row).charAt(column);
    }

    public int getWidth() {
        return content.stream().findFirst().get().length();
    }

    public int getHeight() {
        return content.size();
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("\r\n");
        for (var line : content) {
            builder.append(line + "\r\n");
        }
        return builder.toString();
    }
}
