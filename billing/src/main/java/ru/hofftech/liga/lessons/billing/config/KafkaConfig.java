package ru.hofftech.liga.lessons.billing.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hofftech.liga.lessons.billing.model.kafka.OrderDto;
import ru.hofftech.liga.lessons.billing.service.OrdersListener;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    @Bean
    public Consumer<OrderDto> orders(OrdersListener listener) {
        return message -> {
            listener.process(message);
        };
    }
}
