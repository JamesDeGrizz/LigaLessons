package ru.hofftech.liga.lessons.packageloader.service.logistic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.LogisticService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class OnePerTruckLogisticService implements LogisticService {
    private final TruckServiceFactory truckServiceFactory;

    @Override
    public List<Truck> placePackagesToTrucks(List<Package> packages, int trucksCount) {
        if (packages.size() > trucksCount) {
            throw new IllegalArgumentException("Посылки невозможно отправить заданным алгоритмом, количество посылок превышает количество грузовиков.");
        }

        log.info("Начинаем заполнение грузовиков методом \"один грузовик = одна посылка\"");
        var trucks = new ArrayList<Truck>();

        for (Package pkg : packages) {
            var truckContentService = truckServiceFactory.getTruckService(TRUCK_MAX_WIDTH, TRUCK_MAX_HEIGHT);
            truckContentService.placePackage(pkg, 0, 0);

            trucks.add(truckContentService.getTruck());

            log.info("Посылка \n{} успешно погружена в грузовик", pkg);
        }

        log.info("Заполнение грузовиков методом \"один грузовик = одна посылка\" успешно завершено");
        return trucks;
    }
}
