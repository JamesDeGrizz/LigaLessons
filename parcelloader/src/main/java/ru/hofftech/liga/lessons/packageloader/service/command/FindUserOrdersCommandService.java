package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.mapper.OrderMapper;
import ru.hofftech.liga.lessons.packageloader.model.Order;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.packageloader.validator.FindUserOrdersUserCommandValidator;

import java.util.stream.Collectors;

@AllArgsConstructor
public class FindUserOrdersCommandService {
    private final OrderRepository orderRepository;
    private final FindUserOrdersUserCommandValidator commandValidator;
    private final OrderMapper orderMapper;

    public String execute(FindUserOrdersUserCommandDto command) {
        if (command == null) {
            return "Заказы не могут быть показаны: \nПередан пустой список аргументов";
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            return "Заказы не могут быть показаны: \n" + String.join("\n", validationErrors);
        }

        var userOrders = orderRepository.findById(command.userId())
                .stream()
                .map(orderMapper::toOrderDto)
                .map(Order::toString)
                .collect(Collectors.toList());
        return "Заказы пользователя " + command.userId() + ":\n" + String.join(";\n", userOrders);
    }
}
