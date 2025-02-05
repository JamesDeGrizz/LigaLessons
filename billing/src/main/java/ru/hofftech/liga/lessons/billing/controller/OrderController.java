package ru.hofftech.liga.lessons.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.liga.lessons.billing.model.dto.UserOrdersResponseDto;
import ru.hofftech.liga.lessons.billing.service.BillingService;

import java.util.List;

@RestController
@Validated
@Tag(name = "OrderController", description = "Операции, связанные с биллингом")
@RequestMapping(path = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {
    private final BillingService billingService;

    @GetMapping("/{userid}")
    @Operation(summary = "Получить заказы клиента по имени")
    public List<UserOrdersResponseDto> findUserOrders(
            @PathVariable @NotBlank String userid,
            @RequestParam @Min(0) int offset,
            @RequestParam @Min(1) @Max(1000) int limit
    ) {
        return billingService.findUserOrders(userid, offset, limit);
    }
}
