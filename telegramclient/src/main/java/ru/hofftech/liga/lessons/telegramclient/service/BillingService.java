package ru.hofftech.liga.lessons.telegramclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.telegramclient.model.dto.UserOrdersResponseDto;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final BillingClient billingClient;

    public String getOrders(String userId) {
        return billingClient.findUserOrders(userId, 0, 100).stream()
                .map(UserOrdersResponseDto::toString)
                .collect(Collectors.joining("\n"));
    }
}
