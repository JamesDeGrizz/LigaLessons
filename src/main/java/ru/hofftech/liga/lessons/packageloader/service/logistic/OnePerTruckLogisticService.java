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

@Slf4j
@AllArgsConstructor
public class OnePerTruckLogisticService implements LogisticService {
    private final TruckServiceFactory truckServiceFactory;

    @Override
    public List<Truck> placePackagesToTrucks(List<Package> packages, List<TruckSize> truckSizes) {
        if (packages.size() > truckSizes.size()) {
            throw new IllegalArgumentException("Посылки невозможно отправить заданным алгоритмом, количество посылок превышает количество грузовиков.");
        }

        log.info("Начинаем заполнение грузовиков методом \"один грузовик = одна посылка\"");

        var truckServices = getTruckServices(truckSizes);

        var trucks = placePackages(packages, truckServices);

        log.info("Заполнение грузовиков методом \"один грузовик = одна посылка\" успешно завершено");
        return trucks;
    }

    private List<TruckService> getTruckServices(List<TruckSize> truckSizes) {
        var sortedSizes = truckSizes.stream()
                .sorted((p1, p2) -> p1.getWidth() * p1.getHeight() - p2.getWidth() * p2.getHeight())
                .collect(Collectors.toUnmodifiableList());

        var truckServices = new ArrayList<TruckService>();
        for (var truckSize : sortedSizes) {
            truckServices.add(truckServiceFactory.getTruckService(truckSize));
        }
        return truckServices;
    }

    private List<Truck> placePackages(List<Package> packages, List<TruckService> truckServices) {
        var trucks = new ArrayList<Truck>();

        var truckServicesArray = truckServices.toArray(new TruckService[truckServices.size()]);
        var packagesArray = packages.toArray(new Package[packages.size()]);

        for (var i = 0; i < packages.size(); i++) {
            truckServicesArray[i].placePackage(packagesArray[i], 0, 0);

            trucks.add(truckServicesArray[i].getTruck());

            log.info("Посылка \n{} успешно погружена в грузовик", packagesArray[i]);
        }

        return trucks;
    }
}
