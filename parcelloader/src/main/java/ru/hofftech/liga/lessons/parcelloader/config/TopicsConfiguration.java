package ru.hofftech.liga.lessons.parcelloader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "topics")
public record TopicsConfiguration(Billing billing) {

    public record Billing(String orders) {
    }
}
