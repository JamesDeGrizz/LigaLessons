package ru.hofftech.liga.lessons.packageloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class ReportService {
    private static final String PACKAGES_UNLOAD_MESSAGE = "Из грузовиков извлечены следующие посылки:";
    private static final String TRUCKS_SAVED_TO_FILE_MESSAGE = "Грузовики успешно сохранены в файл ";
    private static final String PACKAGES_SAVED_TO_FILE_MESSAGE = "Посылки успешно сохранены в файл ";

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

    public void reportPackages(List<Package> packages, boolean withCount) {
        log.info(PACKAGES_UNLOAD_MESSAGE);
        if (withCount) {
            var countedPackages = packages.stream()
                    .collect(Collectors.groupingBy(pkg -> pkg.getName(), Collectors.counting()));
            for (var packageGroup : countedPackages.entrySet()) {
                log.info("\n" + packageGroup.getKey() + ";" + packageGroup.getValue());
            }
        }
        else {
            for (var pkg : packages) {
                log.info("\n" + pkg.getName());
            }
        }
    }

    public void saveTrucksToFile(String fileName, List<Truck> trucks) {
        var objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(fileName), trucks);
            log.info(TRUCKS_SAVED_TO_FILE_MESSAGE + fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void savePackagesToFile(String fileName, List<Package> packages, boolean withCount) {
        var sb = new StringBuilder();
        if (withCount) {
            var countedPackages = packages.stream()
                    .collect(Collectors.groupingBy(pkg -> pkg.getName(), Collectors.counting()));
            for (var packageGroup : countedPackages.entrySet()) {
                sb.append(packageGroup.getKey()).append(";").append(packageGroup.getValue()).append("\n");
            }
        }
        else {
            for (var pkg : packages) {
                sb.append(pkg.getName()).append("\n");
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            byte[] strToBytes = sb.toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            log.info(PACKAGES_SAVED_TO_FILE_MESSAGE + fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
