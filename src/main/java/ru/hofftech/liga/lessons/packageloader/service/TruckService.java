package ru.hofftech.liga.lessons.packageloader.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.PlacingPoint;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class TruckService {
    private final Truck truck;

    public TruckService(TruckSize truckSize) {
        truck = new Truck(truckSize);
    }

    public TruckService(Truck truck) {
        this.truck = truck;
    }

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

    public List<Package> getPackages() {
        return truck.getPackages();
    }
}
