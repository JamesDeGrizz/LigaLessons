package ru.hofftech.liga.lessons.billing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "billing")
public record BillingConfiguration(Pricing pricing) {

    public record Pricing(int load, int unload) {
    }
}
