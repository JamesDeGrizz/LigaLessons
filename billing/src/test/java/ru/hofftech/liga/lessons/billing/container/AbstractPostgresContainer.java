package ru.hofftech.liga.lessons.billing.container;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertTrue;

@Testcontainers
@SpringBootTest(properties = "spring.profiles.active=test")
@ExtendWith(SpringExtension.class)
public class AbstractPostgresContainer {

    public static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;
    public static final KafkaContainer KAFKA_CONTAINER;
    private static final String TEST_DATABASE_NAME = "postgres";
    private static final String TEST_USER = "postgres";
    private static final String TEST_PASSWORD = "postgres";
    private static final Network NETWORK = Network.newNetwork();
    public static final Properties properties = new Properties();

    private static void initProp() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("testcontainers.properties")) {
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    static {
        initProp();

        DockerImageName postgresImage = DockerImageName.parse(properties.getProperty("postgres.container.image"))
                .asCompatibleSubstituteFor("postgres");
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(postgresImage)
                .withDatabaseName(TEST_DATABASE_NAME)
                .withUsername(TEST_USER)
                .withPassword(TEST_PASSWORD)
                .withNetwork(NETWORK);
        POSTGRE_SQL_CONTAINER.start();

        DockerImageName kafkaImage = DockerImageName.parse(properties.getProperty("kafka.container.image"))
                .asCompatibleSubstituteFor("confluentinc/cp-kafka");
        KAFKA_CONTAINER = new KafkaContainer(kafkaImage);
        KAFKA_CONTAINER.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                            "spring.datasource.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl() + "&currentSchema=public",
                            "spring.datasource.username=" + TEST_USER,
                            "spring.datasource.password=" + TEST_PASSWORD,
                            "spring.flyway.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
                            "spring.flyway.user=" + TEST_USER,
                            "spring.flyway.password=" + TEST_PASSWORD)
                    .applyTo(configurableApplicationContext.getEnvironment());

            String bootstrapServers = KAFKA_CONTAINER.getBootstrapServers();
            TestPropertyValues.of("spring.cloud.stream.kafka.binder.brokers=" + bootstrapServers)
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeAll
    static void check_containers_running() {
        assertTrue(POSTGRE_SQL_CONTAINER.isRunning());
        assertTrue(KAFKA_CONTAINER.isRunning());
    }
}
