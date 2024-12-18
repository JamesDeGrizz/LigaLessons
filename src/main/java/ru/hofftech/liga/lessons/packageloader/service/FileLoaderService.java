package ru.hofftech.liga.lessons.packageloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileLoaderService {

    public List<Package> getPackages(String fileName) {
        log.debug("Начинается чтение посылок из файла {}", fileName);

        if (fileName == null || fileName.isEmpty() || !Files.exists(Paths.get(fileName))) {
            log.error("Файл не найден");
            return Collections.emptyList();
        }

        var packages = new ArrayList<Package>();
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            var content = new ArrayList<String>();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    packages.add(new Package(new ArrayList<>(content)));

                    content = new ArrayList<>();
                    continue;
                }

                content.add(line);
            }

            if (!content.isEmpty()) {
                var pack = new Package(content);
                packages.add(pack);
            }

            log.debug("Чтение посылок из файла {} успешно завершено, загружено {} посылок", fileName, packages.size());
            return packages;
        }
        catch (Exception e) {
            log.error("Ошибка заполнения списка посылок из файла {}", fileName, e);
            return Collections.emptyList();
        }
    }
}
