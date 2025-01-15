package ru.hofftech.liga.lessons.packageloader.service.logistic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.packageloader.model.Package;
import ru.hofftech.liga.lessons.packageloader.model.Truck;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.service.TruckService;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.LogisticService;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для заполнения грузовиков методом "один грузовик = одна посылка".
 * Этот класс реализует интерфейс {@link LogisticService} и предоставляет методы для размещения одной посылки в одном грузовике.
 */
@Slf4j
@AllArgsConstructor
public class FullFillTruckLogisticService implements LogisticService {
    private final TruckService truckService;

    /**
     * Размещает посылки в грузовиках методом "один грузовик = одна посылка".
     *
     * @param packages список посылок для размещения
     * @param truckSizes список размеров грузовиков
     * @return список грузовиков с размещенными посылками
     * @throws IllegalArgumentException если количество посылок превышает количество грузовиков
     */
    @Override
    public List<Truck> placePackagesToTrucks(List<Package> packages, List<TruckSize> truckSizes) {
        log.info("Начинаем заполнение грузовиков методом \"один грузовик = максимум посылок\"");
        var trucks = getTrucks(truckSizes);

        fillTrucks(packages, trucks);

        log.info("Заполнение грузовиков методом \"один грузовик = максимум посылок\" успешно завершено");
        return trucks;
    }

    /**
     * Получает список сервисов для работы с грузовиками на основе их размеров, отсортированных по возрастанию площади.
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
     * Размещает посылки в грузовиках, один грузовик на одну посылку.
     *
     * @param packages список посылок для размещения
     * @param trucks список грузовиков
     * @return список грузовиков с размещенными посылками
     */
    private void fillTrucks(List<Package> packages, List<Truck> trucks) {
        for (var pkg : packages) {
            var placed = false;
            for (var truck : trucks) {
                if (placed) {
                    break;
                }

                for (int i = 0; i <= truck.getSize().getHeight() - pkg.getHeight(); i++) {
                    if (placed) {
                        break;
                    }

                    for (int j = 0; j <= truck.getSize().getWidth() - pkg.getWidth(); j++) {
                        if (truckService.canPlacePackage(truck, pkg, i, j)) {
                            truckService.placePackage(truck, pkg, i, j);
                            placed = true;
                            break;
                        }
                    }
                }
            }

            if (!placed) {
                throw new IllegalArgumentException("Посылки невозможно отправить заданным алгоритмом, размер посылок превышает ёмкость грузовиков.");
            }
        }
    }
}
