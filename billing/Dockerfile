FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . /app
RUN chmod +x ./gradlew && ./gradlew clean build -x test --no-daemon

FROM openjdk:21-jdk-slim AS builder
WORKDIR /app
COPY --from=build /app/build/libs/billing-1.0-SNAPSHOT.jar /app/billing-1.0-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar billing-1.0-SNAPSHOT.jar extract

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]