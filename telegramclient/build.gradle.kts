plugins {
    id("java")
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.hofftech.liga.lessons"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

object Versions {
    const val SLF4J = "2.0.16"
    const val LOGBACK = "1.5.12"
    const val JACKSON = "2.18.2"
    const val LOMBOK = "1.18.36"
    const val TELEGRAM_BOT = "6.9.7.1"
    const val JUNIT_BOM = "5.10.0"
    const val ASSERTJ_CORE = "3.26.3"
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:${Versions.LOMBOK}")

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.slf4j:slf4j-api:${Versions.SLF4J}")
    implementation("ch.qos.logback:logback-core:${Versions.LOGBACK}")
    implementation("ch.qos.logback:logback-classic:${Versions.LOGBACK}")

    implementation("com.fasterxml.jackson.core:jackson-core:${Versions.JACKSON}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Versions.JACKSON}")

    implementation("org.telegram:telegrambots:${Versions.TELEGRAM_BOT}")

    implementation("org.projectlombok:lombok:${Versions.LOMBOK}")
    compileOnly("org.projectlombok:lombok:${Versions.LOMBOK}")

    testImplementation(platform("org.junit:junit-bom:${Versions.JUNIT_BOM}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:${Versions.ASSERTJ_CORE}")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:3.4.0")
    }
}

tasks.test {
    useJUnitPlatform()
}