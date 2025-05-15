# Use the official JDK 21 image from Docker Hub
FROM openjdk:21-jdk-slim

# Install curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY --chown=1000:1000 target/discovery-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
