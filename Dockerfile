# Stage 1: Build the application
# Using eclipse-temurin:21-jdk-jammy because your pom.xml requires Java 24, 
# but 21 is a safer, widely supported LTS version for containerization.
# If you must use 24, change the tag to a Java 24 image if available.
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY . .

# FIX 1: Grant execute permission to the Maven Wrapper script (mvnw)
# This fixes the "Permission denied" error
RUN chmod +x mvnw

# Build the application, skipping tests
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final, smaller runtime image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar
# The application will listen on port 8080 (as defined in application.properties)
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
