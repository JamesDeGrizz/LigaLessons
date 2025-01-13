package ru.hofftech.liga.lessons.packageloader.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.PlacingPoint;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления грузовиком и размещения посылок в нем.
 * Этот класс предоставляет методы для проверки возможности размещения посылок, их размещения и получения информации о грузовике.
 */
@Slf4j
@Getter
public class TruckService {
    /**
     * Грузовик, в котором размещаются посылки.
     */
    private final Truck truck;

    public TruckService(TruckSize truckSize) {
        truck = new Truck(truckSize);
    }

    public TruckService(Truck truck) {
        this.truck = truck;
    }

    /**
     * Проверяет, можно ли разместить посылку в грузовике на заданной позиции.
     *
     * @param pkg посылка для размещения
     * @param startRow начальная строка для размещения
     * @param startCol начальный столбец для размещения
     * @return true, если посылка может быть размещена, иначе false
     */
    public boolean canPlacePackage(Package pkg, int startRow, int startCol) {
        for (var i = 0; i < pkg.getHeight(); i++) {
            for (var j = 0; j < pkg.getWidth(); j++) {
                if (truck.getContent()[startRow + i][startCol + j] != ' ') {
                    log.debug("Не получится разместить посылку \n{} на позиции: ({}, {})", pkg, startRow, startCol);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Размещает посылку в грузовике на заданной позиции.
     *
     * @param pkg посылка для размещения
     * @param startRow начальная строка для размещения
     * @param startCol начальный столбец для размещения
     */
    public void placePackage(Package pkg, int startRow, int startCol) {
        log.debug("Размещаем посылку \n{} на позиции ({}, {})", pkg, startRow, startCol);

        var placingPoints = new ArrayList<PlacingPoint>();
        for (int i = 0; i < pkg.getHeight(); i++) {
            for (int j = 0; j < pkg.getWidth(); j++) {
                truck.getContent()[startRow + i][startCol + j] = pkg.charAt(i, j);
                placingPoints.add(new PlacingPoint(startRow + i, startCol + j));
            }
        }

        pkg.placePackage(placingPoints);
        truck.getPackages().add(pkg);
    }

    /**
     * Возвращает количество свободного места в грузовике.
     *
     * @return количество свободного места
     */
    public int getFreeSpaceCount() {
        var freeSpaceCount = 0;
        for (var i = 0; i < truck.getContent().length; i++) {
            for (var j = 0; j < truck.getContent()[i].length; j++) {
                if (truck.getContent()[i][j] == ' ') {
                    freeSpaceCount++;
                }
            }
        }
        return freeSpaceCount;
    }

    /**
     * Возвращает список всех посылок, находящихся в грузовике.
     *
     * @return список посылок
     */
    public List<Package> getPackages() {
        return truck.getPackages();
    }
}
