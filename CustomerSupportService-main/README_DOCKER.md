# Docker Setup for Customer Support Service

## Overview
This document describes how to run the Customer Support Service with Kafka using Docker and Docker Compose.

## Prerequisites
- Docker Desktop (or Docker Engine)
- Docker Compose v3.8 or higher

## Quick Start

### Option 1: Using Docker Compose (Recommended)

Run all services together including Kafka and Zookeeper:

```bash
docker-compose up -d
```

This will start:
- **Zookeeper** (port 2181) - Required by Kafka
- **Kafka** (ports 9092, 9101) - Message broker
- **Customer Support Service** (port 8090) - Your microservice

### Option 2: Build and Run Docker Image Manually

Build the Docker image:
```bash
docker build -t customersupport-service:latest .
```

Run the container (requires external Kafka):
```bash
docker run -p 8090:8090 \
  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 \
  -e SERVICES_POLICY_BASE_URL=http://policy-service:8082/api/policies \
  customersupport-service:latest
```

## Configuration

### Kafka Configuration for Docker

The service uses different Kafka bootstrap servers based on the environment:

- **Local Development**: `localhost:9092`
- **Docker Container**: `kafka:29092` (internal inter-container communication)
- **Docker Host Access**: `kafka:9092` (exposed port)

### Key Docker Environment Variables

```
SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092       # Kafka broker (inter-container)
SERVICES_POLICY_BASE_URL=http://policy-service:8082/api/policies  # Policy service URL
SPRING_DATASOURCE_URL=jdbc:h2:mem:customersupport_db              # H2 database
```

### Application Profiles

- **Default** (development): Uses localhost for all services
- **Docker**: Configured in `docker-compose.yml` with service names

## Important: Kafka Configuration Notes

### Issue: Localhost in Docker
❌ **Problem**: `bootstrap-servers=localhost:9092` will NOT work inside Docker
```properties
# This won't work inside the container
spring.kafka.bootstrap-servers=localhost:9092
```

✅ **Solution**: Use Kafka service name from same Docker network
```properties
# Use inter-container communication port
spring.kafka.bootstrap-servers=kafka:29092
```

### Kafka Ports Explanation
- **29092**: Internal communication between containers (PLAINTEXT)
- **9092**: External access from Docker host or outside

### Configuration Fix Applied
The `docker-compose.yml` configures Kafka with:
```yaml
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://kafka:9092
KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
```

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
docker-compose logs -f kafka
```

### Test the service:
```bash
curl http://localhost:8090/actuator/health
```

### Kafka Topic Verification:
```bash
# Enter Kafka container
docker-compose exec kafka bash

# List topics
kafka-topics --bootstrap-server localhost:9092 --list

# Consume from a topic
kafka-console-consumer --bootstrap-server localhost:9092 --topic ticket.created --from-beginning
```

## Stopping Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## Troubleshooting

### Issue: "Connection refused" errors
- Ensure all services show as "healthy" in `docker-compose ps`
- Check that Kafka is fully initialized before starting the application

### Issue: Kafka not reachable from application
- Verify using correct port: `kafka:29092` (not 9092)
- Ensure all services are on the same Docker network

### Issue: Topics not auto-creating
- Check Kafka logs: `docker-compose logs kafka`
- Verify `KAFKA_AUTO_CREATE_TOPICS_ENABLE=true` is set

### Issue: H2 Console not accessible
- Access at: `http://localhost:8090/h2`
- JDBC URL: `jdbc:h2:mem:customersupport_db`
- Username: `sa`, Password: (empty)

## Advanced: Custom Configuration

To use a custom properties file:

1. Create a `application-custom.properties` file
2. Mount it in docker-compose:
```yaml
customersupport-service:
  volumes:
    - ./application-custom.properties:/app/application-custom.properties
  environment:
    SPRING_PROFILES_ACTIVE: custom
```

## Next Steps

1. **For Production**: 
   - Use a production-grade database (MySQL, PostgreSQL) instead of H2
   - Configure Kafka with proper replication factor and retention
   - Implement proper resource limits in docker-compose
   - Use secrets management for credentials

2. **Add Policy Service**: Configure policy-service in docker-compose.yml with its own image

3. **Monitoring**: Consider adding Kafka UI or other monitoring tools
