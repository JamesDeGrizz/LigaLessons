package ru.hofftech.liga.lessons.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.model.Package;
import ru.hofftech.liga.lessons.model.TruckContent;

import java.util.stream.IntStream;

@Slf4j
@Getter
public class TruckContentService {
    private final TruckContent truckContent;

    public TruckContentService(int width, int height) {
        truckContent = new TruckContent(width, height);
        IntStream.range(0, height)
                .forEach(i -> IntStream.range(0, width)
                        .forEach(j -> truckContent.getContent()[i][j] = ' '));
    }

    public boolean canPlacePackage(Package pkg, int startRow, int startCol) {
        for (var i = 0; i < pkg.getHeight(); i++) {
            for (var j = 0; j < pkg.getWidth(); j++) {
                if (truckContent.getContent()[startRow + i][startCol + j] != ' ') {
                    log.debug("Не получится разместить посылку {} на позиции: ({}, {})", pkg, startRow, startCol);
                    return false;
                }
            }
        }

        log.debug("Размещаем посылку {} на позиции ({}, {})", pkg, startRow, startCol);
        return true;
    }

    public void placePackage(Package pkg, int startRow, int startCol) {
        for (int i = 0; i < pkg.getHeight(); i++) {
            for (int j = 0; j < pkg.getWidth(); j++) {
                truckContent.getContent()[startRow + i][startCol + j] = pkg.charAt(i, j);
            }
        }
    }
}
