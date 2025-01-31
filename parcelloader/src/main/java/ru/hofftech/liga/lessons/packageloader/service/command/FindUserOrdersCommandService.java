package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.hofftech.liga.lessons.packageloader.mapper.OrderMapper;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.entity.OrderEntity;
import ru.hofftech.liga.lessons.packageloader.repository.OrderRepository;
import ru.hofftech.liga.lessons.packageloader.validator.FindUserOrdersUserCommandValidator;

@AllArgsConstructor
public class FindUserOrdersCommandService {
    // todo: в настройки
    private static final int PAGE_SIZE = 1000;

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

        var pageNumber = 0;
        var stringBuilder = new StringBuilder();
        Page<OrderEntity> page;

        do {
            // todo: вытащить пагинацию в ендпоинт
            var pageable = PageRequest.of(pageNumber++, PAGE_SIZE);
            page = orderRepository.findByName(command.name(), pageable);
            for (var order : page) {
                stringBuilder
                        .append("\n")
                        .append(orderMapper.toOrderDto(order).toString());
            }
        } while (page.hasNext());

        return "Заказы пользователя " + command.name() + ":" + stringBuilder;
    }
}
