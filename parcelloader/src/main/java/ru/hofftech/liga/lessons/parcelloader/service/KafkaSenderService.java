package ru.hofftech.liga.lessons.parcelloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import ru.hofftech.liga.lessons.parcelloader.config.TopicsConfiguration;
import ru.hofftech.liga.lessons.parcelloader.model.dto.OrderDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSenderService {
    private final StreamBridge streamBridge;
    private final TopicsConfiguration topicsConfiguration;

    public void sendNewOrder(OrderDto order) {
        send(topicsConfiguration.billing().orders(), order);
    }

    private void send(String topic, Object payload) {
        Message<Object> message = MessageBuilder.withPayload(payload)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();

        if (!streamBridge.send(topic, message)){
            log.error("Не удалось отправить сообщение в биллинг сервис {}, заказ не будет сохранён", payload);
        }
    }
}
