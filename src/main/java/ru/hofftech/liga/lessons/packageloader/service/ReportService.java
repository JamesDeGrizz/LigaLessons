package ru.hofftech.liga.lessons.packageloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
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

    public void reportPackages(List<ru.hofftech.liga.lessons.packageloader.model.Package> packages) {
        log.info("Из грузовиков извлечены следующие посылки:");
        for (var pkg : packages) {
            log.info("\n" + pkg.toString());
        }
    }

    public void saveTrucksToFile(String fileName, List<Truck> trucks) {
        var objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(fileName), trucks);
            log.info("Грузовики успешно сохранены в файл " + fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void savePackagesToFile(String fileName, List<ru.hofftech.liga.lessons.packageloader.model.Package> packages) {
        var sb = new StringBuilder();
        for (var pkg : packages) {
            sb.append(pkg).append("\n");
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            byte[] strToBytes = sb.toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            log.info("Посылки успешно сохранены в файл " + fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
