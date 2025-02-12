package ru.hofftech.liga.lessons.consoleclient.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.liga.lessons.consoleclient.service.BillingService;

@ShellComponent
@AllArgsConstructor
public class OrderController {
    private final BillingService billingService;

    @ShellMethod("Выводит данные по заказам пользователя")
    public String orders(@ShellOption(value = "user-id") String userId) {
        return billingService.getOrders(userId);
    }
}
