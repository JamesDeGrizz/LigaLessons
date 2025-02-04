package ru.hofftech.liga.lessons.billing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.liga.lessons.billing.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.billing.model.dto.FindUserOrdersUserResponseDto;
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
    public List<FindUserOrdersUserResponseDto> findUserOrders(@PathVariable @NotEmpty String userid) {
        return billingService.findUserOrders(new FindUserOrdersUserCommandDto(userid));
    }
}
