# --- Build Stage ---
FROM gradle:8.8-jdk17 AS builder
WORKDIR /app

# Copy source code and build settings
COPY --chown=gradle:gradle . .

# Build the Spring Boot application (skip test to speed up docker build)
RUN gradle bootJar --no-daemon -x test

# --- Run Stage ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=builder /app/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar with production profile
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
