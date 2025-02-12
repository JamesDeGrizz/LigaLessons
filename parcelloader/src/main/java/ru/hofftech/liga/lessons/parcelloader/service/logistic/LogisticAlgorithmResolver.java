package ru.hofftech.liga.lessons.parcelloader.service.logistic;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.parcelloader.model.enums.PlacingAlgorithm;
import ru.hofftech.liga.lessons.parcelloader.service.logistic.impl.BalancedFillTruckLogisticService;
import ru.hofftech.liga.lessons.parcelloader.service.logistic.impl.FullFillTruckLogisticService;
import ru.hofftech.liga.lessons.parcelloader.service.logistic.impl.OnePerTruckLogisticService;

@Service
@RequiredArgsConstructor
public class LogisticAlgorithmResolver {
    private final ApplicationContext applicationContext;

    public LogisticService getLogisticService(String algorithm) {
        return switch (getPlacingAlgorithm(algorithm)) {
            case ONE_PER_TRUCK -> applicationContext.getBean(OnePerTruckLogisticService.class);
            case FILL_TRUCK -> applicationContext.getBean(FullFillTruckLogisticService.class);
            case BALANCED -> applicationContext.getBean(BalancedFillTruckLogisticService.class);
            case NONE_OF -> throw new IllegalArgumentException("Не выбран ни один алгоритм");
        };
    }

    private PlacingAlgorithm getPlacingAlgorithm(String algorithmString) {
        var algorithm = Integer.parseInt(algorithmString);
        return PlacingAlgorithm.valueOf(algorithm);
    }
}
