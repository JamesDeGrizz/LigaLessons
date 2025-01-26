FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew && ./gradlew clean build --no-daemon

FROM openjdk:21-jdk-slim AS builder
WORKDIR /app
COPY --from=build /app/build/libs/card-service-1.0.0.jar /app/ConsoleClientApplication.jar
RUN java -Djarmode=layertools -jar ConsoleClientApplication.jar extract

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]