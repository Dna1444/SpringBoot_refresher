# Dockerfile
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app
COPY . .

RUN ./gradlew clean build -x test

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/sprint_boot_refresher-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
