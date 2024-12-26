package ru.hofftech.liga.lessons.packageloader.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Getter
public class TruckService {
    private final Truck truck;

    public TruckService(int width, int height) {
        truck = new Truck(width, height);
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
        for (int i = 0; i < pkg.getHeight(); i++) {
            for (int j = 0; j < pkg.getWidth(); j++) {
                truck.getContent()[startRow + i][startCol + j] = pkg.charAt(i, j);
            }
        }
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
        var packageList = new ArrayList<Package>();

        var contentMap = initPackagesMap();

        var sb = new StringBuilder();
        for (var content : contentMap.entrySet()) {
            for (var i = 0; i < content.getValue() / content.getKey(); i++) {
                for (var j = 0; j < content.getKey(); j++) {
                    sb.append(content.getKey());
                }
                if (content.getKey() <= 5) {
                    packageList.add(new Package(List.of(sb.toString())));
                }
                else if (content.getKey() == 9) {
                    var tmp = sb.toString();
                    packageList.add(new Package(List.of(tmp.substring(0, 3), tmp.substring(3, 6), tmp.substring(6, 9))));
                }
                else {
                    var tmp = sb.toString();
                    packageList.add(new Package(List.of(tmp.substring(0, tmp.length() / 2), tmp.substring(tmp.length() / 2, tmp.length()))));
                }

                sb.setLength(0);
            }
        }

        return packageList;
    }

    private HashMap<Integer, Integer> initPackagesMap() {
        var contentMap = new HashMap<Integer, Integer>();
        contentMap.put(1, 0);
        contentMap.put(2, 0);
        contentMap.put(3, 0);
        contentMap.put(4, 0);
        contentMap.put(5, 0);
        contentMap.put(6, 0);
        contentMap.put(7, 0);
        contentMap.put(8, 0);
        contentMap.put(9, 0);

        for (var i = 0; i < truck.getContent().length; i++) {
            for (var j = 0; j < truck.getContent()[i].length; j++) {
                if (truck.getContent()[i][j] != ' ') {
                    var intValue = Integer.valueOf(truck.getContent()[i][j] - '0');
                    contentMap.put(intValue, contentMap.get(intValue) + 1);
                }
            }
        }
        return contentMap;
    }
}
