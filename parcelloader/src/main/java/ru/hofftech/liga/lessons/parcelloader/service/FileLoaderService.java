package ru.hofftech.liga.lessons.parcelloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.parcelloader.model.Truck;

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

/**
 * Сервис для загрузки данных из файлов.
 * Этот класс предоставляет методы для чтения имен посылок и информации о грузовиках из файлов.
 */
@Slf4j
@RequiredArgsConstructor
public class FileLoaderService {
    private static final String PARCEL_NAME_DELIMITER = ",";
    private final ObjectMapper objectMapper;

    /**
     * Получает имена посылок из указанного файла.
     *
     * @param fileName имя файла, содержащего имена посылок
     * @return массив имен посылок или null, если произошла ошибка
     */
    public String[] getParcelNames(String fileName) {
        try {
            log.debug("Начинается чтение посылок из файла {}", fileName);

            var filePath = getFileFullPath(fileName);
            if (filePath == null) {
                return null;
            }

            return loadParcelNamesFromFile(filePath);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Получает полный путь к файлу.
     *
     * @param fileName имя файла
     * @return полный путь к файлу или null, если файл не найден
     * @throws URISyntaxException если URI синтаксически неверен
     */
    private Path getFileFullPath(String fileName) throws URISyntaxException {
        var filePath = new File(getClass().getClassLoader().getResource(fileName).toURI()).toPath();

        if (fileName == null || fileName.isEmpty() || !Files.exists(filePath)) {
            log.error("Файл не найден");
            return null;
        }

        return filePath;
    }

    /**
     * Загружает имена посылок из файла.
     *
     * @param filePath путь к файлу
     * @return массив имен посылок или null, если произошла ошибка
     */
    private String[] loadParcelNamesFromFile(Path filePath) {
        var parcels = new ArrayList<String>();

        try (var reader = new BufferedReader(new FileReader(filePath.toString()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                parcels.addAll(Arrays.stream(line.split(PARCEL_NAME_DELIMITER)).toList());
            }

            log.debug("Чтение посылок из файла {} успешно завершено, загружено {} посылок", filePath.getFileName(), parcels.size());
            return parcels.toArray(new String[parcels.size()]);
        } catch (Exception e) {
            log.error("Ошибка заполнения списка посылок из файла {}", filePath.getFileName(), e);
            return null;
        }
    }

    /**
     * Получает список грузовиков из указанного файла.
     *
     * @param fileName имя файла, содержащего информацию о грузовиках
     * @return список грузовиков или пустой список, если произошла ошибка
     */
    public List<Truck> getTrucks(String fileName) {
        try {
            log.debug("Начинается чтение грузовиков из файла {}", fileName);

            var trucksFile = new File(getClass().getClassLoader().getResource(fileName).toURI());
            var trucks = objectMapper.readValue(trucksFile, Truck[].class);
            return Arrays.stream(trucks).toList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
