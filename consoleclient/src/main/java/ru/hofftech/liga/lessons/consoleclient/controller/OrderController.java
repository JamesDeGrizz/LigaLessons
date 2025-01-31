package ru.hofftech.liga.lessons.consoleclient.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.liga.lessons.consoleclient.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.consoleclient.service.UserCommandProcessorService;

@ShellComponent
@AllArgsConstructor
public class OrderController {
    private final UserCommandProcessorService userCommandProcessorService;

    @ShellMethod("Выводит данные по заказам пользователя")
    public String orders(@ShellOption(value = "user-id") String userId) {
        return userCommandProcessorService.processCommand(new FindUserOrdersUserCommandDto(userId));
    }
}
