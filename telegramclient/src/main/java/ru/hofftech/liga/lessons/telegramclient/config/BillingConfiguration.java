package ru.hofftech.liga.lessons.telegramclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "billing")
public record BillingConfiguration(Pricing pricing) {

    public record Pricing(int load, int unload) {
    }
}
