package ru.hofftech.liga.lessons.packageloader.service.logistic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.factory.TruckServiceFactory;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.LogisticService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class BalancedFillTruckLogisticService implements LogisticService {
    private final TruckServiceFactory truckServiceFactory;

    @Override
    public List<Truck> placePackagesToTrucks(List<Package> packages, List<TruckSize> truckSizes) {
        log.info("Начинаем заполнение грузовиков методом \"равномерная погрузка по машинам\"");
        var truckServices = getTruckServices(truckSizes);

        distributePackagesToTrucks(packages, truckServices);

        log.info("Заполнение грузовиков методом \"равномерная погрузка по машинам\" успешно завершено");
        return truckServices.stream()
                .map(x -> x.getTruck())
                .collect(Collectors.toUnmodifiableList());
    }

    private List<TruckService> getTruckServices(List<TruckSize> truckSizes) {
        var truckServices = new ArrayList<TruckService>();
        for (var truckSize : truckSizes) {
            truckServices.add(truckServiceFactory.getTruckService(truckSize));
        }
        return truckServices;
    }

    private void distributePackagesToTrucks(List<Package> packages, List<TruckService> truckServices) {
        for (var pkg : packages) {
            var mostFreeTruckService = truckServices.stream()
                    .max(Comparator.comparing(TruckService::getFreeSpaceCount))
                    .get();

            var placed = false;
            for (int i = 0; i <= mostFreeTruckService.getTruck().getSize().getHeight() - pkg.getHeight(); i++) {
                if (placed) {
                    break;
                }

                for (int j = 0; j <= mostFreeTruckService.getTruck().getSize().getHeight() - pkg.getWidth(); j++) {
                    if (mostFreeTruckService.canPlacePackage(pkg, i, j)) {
                        mostFreeTruckService.placePackage(pkg, i, j);
                        placed = true;
                        break;
                    }
                }
            }

            if (!placed) {
                throw new IllegalArgumentException("Посылки невозможно отправить заданным алгоритмом, размер посылок превышает ёмкость грузовиков.");
            }

            log.info("Посылка \n{} успешно погружена в грузовик", pkg);
        }
    }
}
