package ru.hofftech.liga.lessons.consoleclient.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.consoleclient.model.Parcel;
import ru.hofftech.liga.lessons.consoleclient.model.PlacingPoint;
import ru.hofftech.liga.lessons.consoleclient.model.Truck;

import java.util.ArrayList;

/**
 * Сервис для управления грузовиком и размещения посылок в нем.
 * Этот класс предоставляет методы для проверки возможности размещения посылок, их размещения и получения информации о грузовике.
 */
@Slf4j
@Getter
@NoArgsConstructor
public class TruckService {
    /**
     * Проверяет, можно ли разместить посылку в грузовике на заданной позиции.
     *
     * @param parcel посылка для размещения
     * @param startRow начальная строка для размещения
     * @param startCol начальный столбец для размещения
     * @return true, если посылка может быть размещена, иначе false
     */
    public boolean canPlaceParcel(Truck truck, Parcel parcel, int startRow, int startCol) {
        for (var i = 0; i < parcel.getHeight(); i++) {
            for (var j = 0; j < parcel.getWidth(); j++) {
                if (truck.getContent()[startRow + i][startCol + j] != ' ') {
                    log.debug("Не получится разместить посылку \n{} на позиции: ({}, {})", parcel, startRow, startCol);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Размещает посылку в грузовике на заданной позиции.
     *
     * @param parcel посылка для размещения
     * @param startRow начальная строка для размещения
     * @param startCol начальный столбец для размещения
     */
    public void placeParcel(Truck truck, Parcel parcel, int startRow, int startCol) {
        log.debug("Размещаем посылку \n{} на позиции ({}, {})", parcel, startRow, startCol);

        var placingPoints = new ArrayList<PlacingPoint>();
        for (int i = 0; i < parcel.getHeight(); i++) {
            for (int j = 0; j < parcel.getWidth(); j++) {
                truck.getContent()[startRow + i][startCol + j] = parcel.charAt(i, j);
                placingPoints.add(new PlacingPoint(startRow + i, startCol + j));
            }
        }

        parcel.placeParcel(placingPoints);
        truck.getParcels().add(parcel);
    }

    /**
     * Возвращает количество свободного места в грузовике.
     *
     * @return количество свободного места
     */
    public int getFreeSpaceCount(Truck truck) {
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
}
