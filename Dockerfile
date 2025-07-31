# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file to container
COPY target/TaamSmaak-0.0.1-SNAPSHOT.jar app.jar

# Expose port (make sure it matches your Spring Boot app port)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
