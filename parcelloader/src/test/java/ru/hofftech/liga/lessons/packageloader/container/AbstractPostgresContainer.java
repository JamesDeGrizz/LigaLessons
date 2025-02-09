package ru.hofftech.liga.lessons.packageloader.container;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Testcontainers
@SpringBootTest(properties = "spring.profiles.active=test")
@ExtendWith(SpringExtension.class)
public class AbstractPostgresContainer {

    final static String POSTGRES_VERSION = "postgres:latest";
    final static String DATABASE_NAME = "test_db";
    final static String DATABASE_USER = "test";
    final static String DATABASE_PASSWORD = "test";

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(POSTGRES_VERSION)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USER)
            .withPassword(DATABASE_PASSWORD);

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = "jdbc:postgresql://"
                + postgresContainer.getHost()
                + ":" + postgresContainer.getMappedPort(5432)
                + "/" + postgresContainer.getDatabaseName();

        registry.add("spring.datasource.url", () -> jdbcUrl);
    }
}
