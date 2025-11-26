# ===========================
# Stage 1: Build JAR using Maven
# ===========================
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy all project files
COPY . .

# Build the project (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# ===========================
# Stage 2: Run JAR
# ===========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy JAR file from builder stage
COPY --from=builder /app/target/moneymanager-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "moneymanager-v1.0.jar"]
