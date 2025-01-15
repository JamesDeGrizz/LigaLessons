package ru.hofftech.liga.lessons.packageloader.service.logistic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.LogisticService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Сервис для равномерного распределения посылок по грузовикам.
 * Этот класс реализует интерфейс {@link LogisticService} и предоставляет методы для равномерного распределения посылок по грузовикам.
 */
@Slf4j
@AllArgsConstructor
public class BalancedFillTruckLogisticService implements LogisticService {
    private final TruckService truckService;

    /**
     * Размещает посылки в грузовиках методом "равномерная погрузка по машинам".
     *
     * @param packages список посылок для размещения
     * @param truckSizes список размеров грузовиков
     * @return список грузовиков с размещенными посылками
     */
    @Override
    public List<Truck> placePackagesToTrucks(List<Package> packages, List<TruckSize> truckSizes) {
        log.info("Начинаем заполнение грузовиков методом \"равномерная погрузка по машинам\"");
        var trucks = getTrucks(truckSizes);

        distributePackagesToTrucks(packages, trucks);

        log.info("Заполнение грузовиков методом \"равномерная погрузка по машинам\" успешно завершено");
        return trucks;
    }

    /**
     * Получает список грузовов на основе их размеров.
     *
     * @param truckSizes список размеров грузовиков
     * @return список сервисов для работы с грузовиками
     */
    private List<Truck> getTrucks(List<TruckSize> truckSizes) {
        var trucks = new ArrayList<Truck>();
        for (var truckSize : truckSizes) {
            trucks.add(new Truck(truckSize));
        }
        return trucks;
    }

    /**
     * Распределяет посылки по грузовикам.
     *
     * @param packages список посылок для размещения
     * @param trucks список грузовиков
     */
    private void distributePackagesToTrucks(List<Package> packages, List<Truck> trucks) {
        for (var pkg : packages) {
            var mostFreeTruck = trucks.stream()
                    .max(Comparator.comparing(x -> truckService.getFreeSpaceCount(x)))
                    .get();

            var placed = false;
            for (int i = 0; i <= mostFreeTruck.getSize().getHeight() - pkg.getHeight(); i++) {
                if (placed) {
                    break;
                }

                for (int j = 0; j <= mostFreeTruck.getSize().getHeight() - pkg.getWidth(); j++) {
                    if (truckService.canPlacePackage(mostFreeTruck, pkg, i, j)) {
                        truckService.placePackage(mostFreeTruck, pkg, i, j);
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
