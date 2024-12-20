package ru.hofftech.liga.lessons.packageloader.service;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
public class ReportService {
    public void reportTruckContent(char[][] content) {
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

        log.info(builder.toString());
    }
}
