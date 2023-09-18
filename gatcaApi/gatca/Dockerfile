FROM openjdk:19-alpine
LABEL authors="Miguel"

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY build/libs/gatca-0.0.1.jar app.jar

# Expose the port your application runs on (replace with your actual port)
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]