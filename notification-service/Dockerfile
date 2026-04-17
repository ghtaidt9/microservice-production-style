# ============ BUILD STAGE ==========
FROM gradle:8.5-jdk17 AS builder
WORKDIR /build
COPY . .
RUN gradle clean build -x test

# ============ RUNTIME STAGE ========
FROM eclipse-temurin:17-jdk-jammy

RUN apt-get update && apt-get install -y curl

WORKDIR /app

# Security: non-root user
RUN useradd -ms /bin/bash appuser
COPY --from=builder /build/build/libs/*.jar app.jar
RUN chown -R appuser:appuser /app
USER appuser

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]