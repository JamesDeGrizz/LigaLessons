FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . /app
RUN pwd
RUN ls -l gradlew
RUN chmod +x /app/gradlew && /app/gradlew clean build -x test --no-daemon

FROM openjdk:21-jdk-slim AS builder
WORKDIR /app
RUN ls -la
COPY --from=build /app/build/libs/ParcelLoaderApplication-1.0.0.jar /app/ParcelLoaderApplication.jar
RUN ls -la
RUN java -Djarmode=layertools -jar ParcelLoaderApplication.jar extract

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]