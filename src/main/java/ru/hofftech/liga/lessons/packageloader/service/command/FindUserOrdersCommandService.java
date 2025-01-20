package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.Order;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.FindUserOrdersUserCommandValidator;

import java.util.stream.Collectors;

@AllArgsConstructor
public class FindUserOrdersCommandService implements UserCommandService {
    private final OrderRepository orderRepository;
    private final FindUserOrdersUserCommandValidator commandValidator;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Заказы не могут быть показаны: \nПередан пустой список аргументов";
        }
        if (!(command instanceof FindUserOrdersUserCommandDto)) {
            return "Заказы не могут быть показаны: \nПередана команда неправильного типа";
        }

        var castedCommand = (FindUserOrdersUserCommandDto) command;
        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Заказы не могут быть показаны: \n" + String.join("\n", validationErrors);
        }

        var userOrders = orderRepository.findOrdersByUserId(castedCommand.userId())
                .stream()
                .map(Order::toString)
                .collect(Collectors.toList());
        return "Заказы пользователя " + castedCommand.userId() + ":\n" + String.join(";\n", userOrders);
    }
}
