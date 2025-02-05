package ru.hofftech.liga.lessons.billing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "billing.pricing")
public record BillingConfiguration(int load, int unload) {
}
