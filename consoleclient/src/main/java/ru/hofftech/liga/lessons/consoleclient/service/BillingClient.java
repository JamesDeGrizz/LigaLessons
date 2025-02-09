package ru.hofftech.liga.lessons.consoleclient.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import ru.hofftech.liga.lessons.consoleclient.model.dto.UserOrdersResponseDto;

import java.util.List;

public interface BillingClient {
    @GetExchange("/api/v1/orders/{userid}")
    List<UserOrdersResponseDto> findUserOrders(@PathVariable @NotBlank String userid,
                                               @RequestParam @Min(0) int offset,
                                               @RequestParam @Min(1) @Max(1000) int limit);
}
