package ru.hofftech.liga.lessons.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.model.Package;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileRepository {

    public List<Package> getPackages(String fileName) {
        log.debug("Начинается чтение посылок из файла {}", fileName);

        if (fileName == null || fileName.isEmpty() || !Files.exists(Paths.get(fileName))) {
            log.error("Файл не найден");
            return Collections.emptyList();
        }

        var packages = new ArrayList<Package>();
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            var width = 0;
            var height = 0;
            var content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    var pack = new Package(width, height, content.toString());
                    if (pack.valid()) {
                        packages.add(pack);
                    }
                    else {
                        printLogInvalidPackage(width, height, content);
                    }

                    width = 0;
                    height = 0;
                    content = new StringBuilder();
                    continue;
                }

                width = width < line.length() ? line.length() : width;
                height++;
                content.append(line);
            }

            if (!content.isEmpty()) {
                var pack = new Package(width, height, content.toString());
                if (pack.valid()) {
                    packages.add(pack);
                }
                else {
                    printLogInvalidPackage(width, height, content);
                }
            }

            log.debug("Чтение посылок из файла {} успешно завершено, загружено {} посылок", fileName, packages.size());
            return packages;
        }
        catch (Exception e) {
            log.error("Ошибка заполнения списка посылок из файла {}", fileName, e);
            return Collections.emptyList();
        }
    }

    private static void printLogInvalidPackage(int width, int height, StringBuilder content) {
        log.warn("Получена невалидная посылка, ширина {}, высота {}, содержимое {}", width, height, content);
    }
}
