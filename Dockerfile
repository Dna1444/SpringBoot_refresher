 Stage 1: Build the app using Gradle
FROM gradle:8.4-jdk17 AS builder
WORKDIR /app

# Copy Gradle config and build files
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle
COPY src ./src

# Build the application (skip tests for faster builds)
RUN gradle build -x test

# Stage 2: Run the app using a smaller base image
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy only the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]