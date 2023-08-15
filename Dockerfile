# Use officiel Maven image with OpenJDK 17 as builder
FROM maven:3.8.4-openjdk-17 AS builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Use OpenJDK 17 for run-time
FROM openjdk:17-jdk-slim

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/*.jar /app.jar

# Specifies a command that will always be executed when the container starts.
ENTRYPOINT ["java","-jar","/app.jar"]

# Expose the port
ENV PORT ${DOCKER_ENV_PORT}
EXPOSE ${DOCKER_EXPOSE_PORT}