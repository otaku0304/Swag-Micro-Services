# Use the official JDK 21 image from Docker Hub
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container (with correct ownership)
COPY --chown=1000:1000 target/config-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
