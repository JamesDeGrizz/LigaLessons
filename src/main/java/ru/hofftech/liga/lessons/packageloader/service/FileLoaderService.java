package ru.hofftech.liga.lessons.packageloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileLoaderService {

    public String[] getPackageNames(String fileName) {
        try {
            log.debug("Начинается чтение посылок из файла {}", fileName);

            var filePath = getFileFullPath(fileName);
            if (filePath == null) {
                return null;
            }

            return loadPackageNamesFromFile(filePath);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private Path getFileFullPath(String fileName) throws URISyntaxException {
        var filePath = new File(getClass().getClassLoader().getResource(fileName).toURI()).toPath();

        if (fileName == null || fileName.isEmpty() || !Files.exists(filePath)) {
            log.error("Файл не найден");
            return null;
        }

        return filePath;
    }

    private String[] loadPackageNamesFromFile(Path filePath) {
        var packages = new ArrayList<String>();

        try (var reader = new BufferedReader(new FileReader(filePath.toString()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                packages.addAll(Arrays.stream(line.split("\\\\n")).toList());
            }

            log.debug("Чтение посылок из файла {} успешно завершено, загружено {} посылок", filePath.getFileName(), packages.size());
            return packages.toArray(new String[packages.size()]);
        } catch (Exception e) {
            log.error("Ошибка заполнения списка посылок из файла {}", filePath.getFileName(), e);
            return null;
        }
    }

    public List<Truck> getTrucks(String fileName) {
        try {
            log.debug("Начинается чтение грузовиков из файла {}", fileName);

            var objectMapper = new ObjectMapper();
            var trucksFile = new File(getClass().getClassLoader().getResource(fileName).toURI());
            var trucks = objectMapper.readValue(trucksFile, Truck[].class);
            return Arrays.stream(trucks).toList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
