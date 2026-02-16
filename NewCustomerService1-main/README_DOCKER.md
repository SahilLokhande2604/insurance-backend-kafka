# Docker Setup for Customer Support Service

## Overview
This document describes how to run the Customer Support Service using Docker and Docker Compose.

## Prerequisites
- Docker Desktop (or Docker Engine)
- Docker Compose v3.8 or higher

## Quick Start

### Option 1: Using Docker Compose (Recommended)

Run the service:

```bash
docker-compose up -d
```

This will start:
- **Customer Support Service** (port 8090) - Your microservice

### Option 2: Build and Run Docker Image Manually

Build the Docker image:
```bash
docker build -t customersupport-service:latest .
```

Run the container:
```bash
docker run -p 8090:8090 \
  -e SERVICES_POLICY_BASE_URL=http://policy-service:8082/api/policies \
  customersupport-service:latest
```

## Configuration

### Key Docker Environment Variables

```
SERVICES_POLICY_BASE_URL=http://policy-service:8082/api/policies  # Policy service URL
SPRING_DATASOURCE_URL=jdbc:h2:mem:customersupport_db              # H2 database
```

### Application Profiles

- **Default** (development): Uses localhost for all services
- **Docker**: Configured in `docker-compose.yml` with service names

## Verification

### Check if services are running:
```bash
docker-compose ps
```

### View service logs:
```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f customersupport-service
```

### Test the service:
```bash
curl http://localhost:8090/actuator/health
```

### H2 Console not accessible
- Access at: `http://localhost:8090/h2`
- JDBC URL: `jdbc:h2:mem:customersupport_db`
- Username: `sa`, Password: (empty)

## Stopping Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```
