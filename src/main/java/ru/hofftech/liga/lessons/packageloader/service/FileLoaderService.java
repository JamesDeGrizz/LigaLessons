package ru.hofftech.liga.lessons.packageloader.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FileLoaderService {

    public List<Package> getPackages(String fileName) {
        try {
            log.debug("Начинается чтение посылок из файла {}", fileName);

            var filePath = getFileFullPath(fileName);
            if (filePath == null) {
                return Collections.emptyList();
            }

            return loadPackagesFromFile(filePath);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
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

    private List<Package> loadPackagesFromFile(Path filePath) {
        var packages = new ArrayList<Package>();

        try (var reader = new BufferedReader(new FileReader(filePath.toString()))) {
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

            log.debug("Чтение посылок из файла {} успешно завершено, загружено {} посылок", filePath.getFileName(), packages.size());
            return packages;
        } catch (Exception e) {
            log.error("Ошибка заполнения списка посылок из файла {}", filePath.getFileName(), e);
            return Collections.emptyList();
        }
    }

    public List<Truck> getTrucks(String fileName) {
        try {
            log.debug("Начинается чтение грузовиков из файла {}", fileName);

            var trucks = new ArrayList<Truck>();

            var root = getRootNode(fileName);

            for (var rootNode : root) {
                var jsonNode = rootNode.fields().next().getValue();

                var content = parseNodeToTruckContent(jsonNode);

                trucks.add(new Truck(content));
            }

            log.debug("Чтение грузовиков из файла {} успешно завершено, загружено {} грузовиков", fileName, trucks.size());
            return trucks;
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    private JsonNode getRootNode(String fileName) throws IOException, URISyntaxException {
        var objectMapper = new ObjectMapper();
        var root = objectMapper.readTree(new File(getClass().getClassLoader().getResource(fileName).toURI()));
        return root;
    }

    private char[][] parseNodeToTruckContent(JsonNode jsonNode) {
        // исходим из того, что содержимое грузовика всегда прямоугольно
        var content = new char[jsonNode.size()][jsonNode.get(0).asText().length()];

        for (int i = 0; i < jsonNode.size(); i++) {
            content[i] = jsonNode.get(i).asText().toCharArray();
        }
        return content;
    }
}
