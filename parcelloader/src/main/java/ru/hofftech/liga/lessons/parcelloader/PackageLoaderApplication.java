package ru.hofftech.liga.lessons.parcelloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PackageLoaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PackageLoaderApplication.class, args);
    }
}