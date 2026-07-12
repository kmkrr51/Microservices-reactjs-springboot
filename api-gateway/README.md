# API Gateway Module

## Overview

Spring Cloud Gateway for routing requests to microservices with authentication and security.

## Components

- **GatewayConfig**: Route configuration for microservices
- **ApiGatewayApplication**: Main application entry point
- **application.yml**: Gateway configuration

## Routes

- `/api/incidents/**` → incident-service:8080
- `/api/users/**` → user-service:8080
- `/api/cmdb/**` → cmdb-service:8080

## Features

- Request routing to microservices
- Security filters
- Logging and monitoring
- Health checks

## Build & Run

```bash
mvn clean install -pl api-gateway
mvn spring-boot:run -pl api-gateway
```

## Configuration

Edit `application.yml` to add/modify routes and security settings.

---

**Status**: ✅ Complete  
**Last Updated**: July 12, 2026
