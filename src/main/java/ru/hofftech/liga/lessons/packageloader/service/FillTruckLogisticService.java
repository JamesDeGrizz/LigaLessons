package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class FillTruckLogisticService implements LogisticService {
    private final TruckServiceFactory truckContentServiceFactory;

    private final int TRUCK_MAX_WIDTH = 6;
    private final int TRUCK_MAX_HEIGHT = 6;

    @Override
    public List<Truck> placePackagesToTrucks(List<Package> packages) {
        log.info("Начинаем заполнение грузовиков методом \"один грузовик = максимум посылок\"");
        var trucks = new ArrayList<Truck>();

        var truckContentService = truckContentServiceFactory.getTruckService(TRUCK_MAX_WIDTH, TRUCK_MAX_HEIGHT);
        for (var pkg : packages) {
            boolean placed = false;

            for (int i = 0; i <= TRUCK_MAX_HEIGHT - pkg.getHeight(); i++) {
                if (placed) break;
                for (int j = 0; j <= TRUCK_MAX_WIDTH - pkg.getWidth(); j++) {
                    if (truckContentService.canPlacePackage(pkg, i, j)) {
                        truckContentService.placePackage(pkg, i, j);
                        placed = true;
                        break;
                    }
                }
            }

            if (!placed) {
                log.warn("Посылка не поместилась:\n{}", pkg);
            }
        }

        trucks.add(truckContentService.getTruck());
        log.info("Заполнение грузовиков методом \"один грузовик = максимум посылок\" успешно завершено");
        return trucks;
    }
}
