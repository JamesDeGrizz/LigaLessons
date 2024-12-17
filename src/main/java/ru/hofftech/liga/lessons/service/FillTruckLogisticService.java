package ru.hofftech.liga.lessons.service;

import lombok.extern.slf4j.Slf4j;
import ru.hofftech.liga.lessons.model.Package;

import java.util.List;

@Slf4j
public class FillTruckLogisticService implements LogisticService {
    @Override
    public void placePackagesToTrucks(List<Package> packages) {
        log.info("Начинаем заполнение грузовиков методом \"один грузовик = максимум посылок\"");

        // здесь могла бы быть ваша реклама

        log.info("Заполнение грузовиков методом \"один грузовик = максимум посылок\" успешно завершено");
    }
}
