package ru.hofftech.liga.lessons.packageloader.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для создания отчетов и сохранения данных о посылках.
 * Этот класс предоставляет методы для вывода содержимого посылок, создания отчетов о посылках и сохранения данных в файлы.
 */
@Slf4j
public class ReportPackageService {
    private static final String PACKAGES_UNLOAD_MESSAGE = "Из грузовиков извлечены следующие посылки:";
    private static final String PACKAGES_SAVED_TO_FILE_MESSAGE = "Посылки успешно сохранены в файл ";

    /**
     * Выводит отчет о посылках.
     *
     * @param packages список посылок
     * @param withCount флаг, указывающий, нужно ли включать количество посылок
     */
    public void reportPackages(List<Package> packages, boolean withCount) {
        log.info(PACKAGES_UNLOAD_MESSAGE);
        if (withCount) {
            packages.stream()
                .collect(Collectors.groupingBy(pkg -> pkg.getName(), Collectors.counting()))
                .forEach((name, count) -> log.info("\n" + name + ";" + count));
        }
        else {
            for (var pkg : packages) {
                log.info("\n" + pkg.getName());
            }
        }
    }

    /**
     * Сохраняет информацию о посылках в файл.
     *
     * @param fileName имя файла для сохранения
     * @param packages список посылок
     * @param withCount флаг, указывающий, нужно ли включать количество посылок
     */
    public void savePackagesToFile(String fileName, List<Package> packages, boolean withCount) {
        var stringBuilder = new StringBuilder();
        if (withCount) {
            packages.stream()
                    .collect(Collectors.groupingBy(pkg -> pkg.getName(), Collectors.counting()))
                    .forEach((name, count) -> stringBuilder
                            .append(name)
                            .append(";")
                            .append(count)
                            .append("\n"));
        }
        else {
            for (var pkg : packages) {
                stringBuilder
                        .append(pkg.getName())
                        .append("\n");
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName);) {
            byte[] strToBytes = stringBuilder.toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            log.info(PACKAGES_SAVED_TO_FILE_MESSAGE + fileName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
