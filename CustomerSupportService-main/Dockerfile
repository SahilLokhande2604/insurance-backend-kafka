# Stage 1: Build stage
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /app/target/customersupport-service-*.jar app.jar

# Expose port
EXPOSE 8090

# Set environment variables for Kafka and other services
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 \
    SERVICES_POLICY_BASE_URL=http://policy-service:8082/api/policies

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD java -cp app.jar noop || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
