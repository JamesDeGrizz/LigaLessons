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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для заполнения грузовиков методом "один грузовик = одна посылка".
 * Этот класс реализует интерфейс {@link LogisticService} и предоставляет методы для размещения одной посылки в одном грузовике.
 */
@Slf4j
@AllArgsConstructor
public class FullFillTruckLogisticService implements LogisticService {
    /**
     * Фабрика сервисов для работы с грузовиками.
     */
    private final TruckServiceFactory truckServiceFactory;

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
        var truckServices = getTruckServices(truckSizes);

        fillTrucks(packages, truckServices);

        log.info("Заполнение грузовиков методом \"один грузовик = максимум посылок\" успешно завершено");
        return truckServices.stream()
                .map(x -> x.getTruck())
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Получает список сервисов для работы с грузовиками на основе их размеров, отсортированных по возрастанию площади.
     *
     * @param truckSizes список размеров грузовиков
     * @return список сервисов для работы с грузовиками
     */
    private List<TruckService> getTruckServices(List<TruckSize> truckSizes) {
        var truckServices = new ArrayList<TruckService>();
        for (var truckSize : truckSizes) {
            truckServices.add(truckServiceFactory.getTruckService(truckSize));
        }
        return truckServices;
    }

    /**
     * Размещает посылки в грузовиках, один грузовик на одну посылку.
     *
     * @param packages список посылок для размещения
     * @param truckServices список сервисов для работы с грузовиками
     * @return список грузовиков с размещенными посылками
     */
    private void fillTrucks(List<Package> packages, List<TruckService> truckServices) {
        for (var pkg : packages) {
            var placed = false;
            for (var truckService : truckServices) {
                if (placed) {
                    break;
                }

                for (int i = 0; i <= truckService.getTruck().getSize().getHeight() - pkg.getHeight(); i++) {
                    if (placed) {
                        break;
                    }

                    for (int j = 0; j <= truckService.getTruck().getSize().getWidth() - pkg.getWidth(); j++) {
                        if (truckService.canPlacePackage(pkg, i, j)) {
                            truckService.placePackage(pkg, i, j);
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
