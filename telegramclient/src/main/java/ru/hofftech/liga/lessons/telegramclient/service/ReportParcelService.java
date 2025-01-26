package ru.hofftech.liga.lessons.telegramclient.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.telegramclient.model.Parcel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для создания отчетов и сохранения данных о посылках.
 * Этот класс предоставляет методы для вывода содержимого посылок, создания отчетов о посылках и сохранения данных в файлы.
 */
@Slf4j
public class ReportParcelService {
    private static final String PARCELS_UNLOAD_MESSAGE = "Из грузовиков извлечены следующие посылки:";
    private static final String PARCELS_SAVED_TO_FILE_MESSAGE = "Посылки успешно сохранены в файл ";

    /**
     * Выводит отчет о посылках.
     *
     * @param parcels список посылок
     * @param withCount флаг, указывающий, нужно ли включать количество посылок
     */
    public void reportParcels(List<Parcel> parcels, boolean withCount) {
        log.info(PARCELS_UNLOAD_MESSAGE);
        if (withCount) {
            parcels.stream()
                .collect(Collectors.groupingBy(Parcel::getName, Collectors.counting()))
                .forEach((name, count) -> log.info("\n" + name + ";" + count));
        }
        else {
            for (var parcel : parcels) {
                log.info("\n" + parcel.getName());
            }
        }
    }

    /**
     * Сохраняет информацию о посылках в файл.
     *
     * @param fileName имя файла для сохранения
     * @param parcels список посылок
     * @param withCount флаг, указывающий, нужно ли включать количество посылок
     */
    public void saveParcelsToFile(String fileName, List<Parcel> parcels, boolean withCount) {
        var stringBuilder = new StringBuilder();
        if (withCount) {
            parcels.stream()
                    .collect(Collectors.groupingBy(Parcel::getName, Collectors.counting()))
                    .forEach((name, count) -> stringBuilder
                            .append(name)
                            .append(";")
                            .append(count)
                            .append("\n"));
        }
        else {
            for (var parcel : parcels) {
                stringBuilder
                        .append(parcel.getName())
                        .append("\n");
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName);) {
            byte[] strToBytes = stringBuilder.toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            log.info(PARCELS_SAVED_TO_FILE_MESSAGE + fileName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
