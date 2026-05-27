# Microservice Production Style

A sample microservices system built with Spring Boot, Gradle, Docker Compose, Kubernetes manifests, and observability tooling.

## Overview

This repository contains a multi-service architecture with the following components:

- `api-gateway` - gateway service for routing requests to downstream microservices
- `discovery-service` - service discovery / registry
- `user-service` - user management microservice
- `product-service` - product management microservice
- `order-service` - ordering microservice
- `inventory-service` - inventory microservice
- `payment-service` - payment processing microservice
- `notification-service` - async notification microservice
- `analytics-service` - analytics microservice
- `common-events` - shared event definitions published to a local Nexus snapshot repository

Infrastructure and supporting services include:

- `nexus` - local Maven/Nexus repository for snapshot publishing
- `zookeeper` and `kafka` - messaging infrastructure
- `redis` - caching/session store
- `mysql` containers for service-specific databases
- `prometheus` - metrics collection
- `grafana` - dashboarding
- `jaeger` - distributed tracing

## Repository Layout

- `docker-compose.yml` - orchestration of local containers for development and testing
- `k8s/` - Kubernetes deployment and configuration manifests
- `monitoring/` - Prometheus and Grafana configuration
- `*service*/` - service-specific Gradle projects and Dockerfiles

## Prerequisites

- Docker
- Docker Compose
- Java 17 (for local Gradle build) if you need to build images locally outside Docker
- Gradle wrapper is included in each service project

## Environment Variables

The compose setup uses environment variables for credentials and telemetry configuration:

- `NEXUS_USERNAME` - Nexus repository username
- `NEXUS_PASSWORD` - Nexus repository password
- `MYSQL_USER` - MySQL user for application services
- `MYSQL_PASSWORD` - MySQL password for application services
- `OTEL_EXPORTER_OTLP_ENDPOINT` - OTLP collector endpoint for tracing/metrics

> Example: create a `.env` file in the repository root with required values before starting.

## Run Locally with Docker Compose

1. Create a `.env` file in the root with the required environment variables.
2. Start the entire stack:

```bash
docker compose up --build
```

3. Stop the stack:

```bash
docker compose down
```

## Key Ports

- API Gateway: `http://localhost:8080`
- Service Registry: `http://localhost:8761`
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000`
- Jaeger UI: `http://localhost:16686`
- Nexus: `http://localhost:8081`

## Kubernetes

Kubernetes manifests are stored under `k8s/`.

- `k8s/services/` contains service deployment YAMLs
- `k8s/config/` contains service configuration and ingress manifests
- `k8s/infra/` contains infrastructure manifests for Jaeger, Kafka, MySQL, Redis, and other supporting components

Use these manifests to deploy the architecture to a Kubernetes cluster after adapting configuration to your target environment.

## Build Process

Each service is a Gradle project that produces a deployable WAR artifact.

Typical build command inside a service folder:

```bash
./gradlew clean build
```

## Notes

- The `common-events` project is published to Nexus before services that depend on shared event types are built.
- Services depend on wellness checks for MySQL, Redis, Kafka, and discovery to ensure startup ordering.
- All service Dockerfiles use a multi-stage build with Gradle and run on Eclipse Temurin Java 17.

## Contact

For questions about this repository, inspect service projects under the root directories or open the compose and Kubernetes manifests for configuration details.
