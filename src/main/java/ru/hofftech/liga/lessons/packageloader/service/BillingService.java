package ru.hofftech.liga.lessons.packageloader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ru.hofftech.liga.lessons.packageloader.model.Order;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.config.BillingConfiguration;
import ru.hofftech.liga.lessons.packageloader.model.enums.Command;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;

import java.util.Date;
import java.util.List;

@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class BillingService {
    private final OrderRepository orderRepository;
    private final BillingConfiguration billingConfiguration;

    public void saveOrder(String userId, Command command, int trucksCount, List<Parcel> parcels) {
        int pricePerCell;
        String commandStr;
        switch (command) {
            case Command.LOAD_PARCELS:
                pricePerCell = billingConfiguration.pricing().load();
                commandStr = "Погрузка";
                break;
            case Command.UNLOAD_TRUCKS:
                pricePerCell = billingConfiguration.pricing().unload();
                commandStr = "Разгрузка";
                break;
            default:
                throw new UnsupportedOperationException("Команда " + command + " бесплатна");
        };

        var totalPrice = parcels.stream()
                .mapToInt(x -> x.getSize() * pricePerCell)
                .sum();

        var order = new Order(userId, new Date(), commandStr, trucksCount, parcels.size(), totalPrice);
        orderRepository.saveOrder(order);
    }
}
