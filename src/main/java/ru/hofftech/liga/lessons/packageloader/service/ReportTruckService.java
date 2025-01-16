package ru.hofftech.liga.lessons.packageloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Сервис для создания отчетов и сохранения данных о грузовиках.
 * Этот класс предоставляет методы для вывода содержимого грузовиков и сохранения данных в файлы.
 */
@Slf4j
public class ReportTruckService {
    private static final String TRUCKS_SAVED_TO_FILE_MESSAGE = "Грузовики успешно сохранены в файл ";

    /**
     * Выводит содержимое грузовика в виде отчета.
     *
     * @param content содержимое грузовика в виде двумерного массива символов
     */
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

    /**
     * Сохраняет информацию о грузовиках в файл.
     *
     * @param fileName имя файла для сохранения
     * @param trucks список грузовиков
     */
    public void saveTrucksToFile(String fileName, List<Truck> trucks) {
        var objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(fileName), trucks);
            log.info(TRUCKS_SAVED_TO_FILE_MESSAGE + fileName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
