package ru.hofftech.liga.lessons.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.model.Package;
import ru.hofftech.liga.lessons.model.Truck;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OnePerTruckLogisticService implements LogisticService {
    private final int TRUCK_MAX_WIDTH = 6;
    private final int TRUCK_MAX_HEIGHT = 6;

    @Override
    public List<Truck> placePackagesToTrucks(List<Package> packages) {
        log.info("Начинаем заполнение грузовиков методом \"один грузовик = одна посылка\"");
        var trucks = new ArrayList<Truck>();

        for (Package pkg : packages) {
            var truckContentService = TruckContentServiceFactory.getTruckContentService(TRUCK_MAX_WIDTH, TRUCK_MAX_HEIGHT);
            truckContentService.placePackage(pkg, 0, 0);

            var truck = new Truck(truckContentService.getTruckContent());
            trucks.add(truck);

            log.info("Посылка {} успешно погружена в грузовик", pkg);
        }

        log.info("Заполнение грузовиков методом \"один грузовик = одна посылка\" успешно завершено");
        return trucks;
    }
}
