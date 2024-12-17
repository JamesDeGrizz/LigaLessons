package ru.hofftech.liga.lessons.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.model.Package;
import ru.hofftech.liga.lessons.model.Truck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class OnePerTruckLogisticService implements LogisticService {
    @Override
    public void placePackagesToTrucks(List<Package> packages) {
        log.info("Начинаем заполнение грузовиков методом \"один грузовик = одна посылка\"");

        for (Package pack : packages) {
            var truckContent =  new HashMap<Integer, String>();

            for (var i = 0; i < pack.getMaxHeight(); i++) {
                var indexFrom = i * pack.getMaxWidth();
                var indexTo = indexFrom + pack.getMaxWidth() > pack.getContent().length() ? pack.getContent().length() : indexFrom + pack.getMaxWidth();
                truckContent.put(i, pack.getContent().substring(indexFrom, indexTo));
            }

            // тут можно было бы собрать грузовики в список и куда-нибудь его отдать, но в нашем случае достаточно просто распечатать
            var truck = new Truck(truckContent);
            log.info("Заполнен грузовик:");
            log.info(truck.toString());
        }

        log.info("Заполнение грузовиков методом \"один грузовик = одна посылка\" успешно завершено");
    }
}
