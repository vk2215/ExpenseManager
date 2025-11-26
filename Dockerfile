# Stage 1 - Build
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

COPY . .

# Give execute permission
RUN chmod +x mvnw

# Build project
RUN ./mvnw clean package -DskipTests

# Stage 2 - Run
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 9090
ENTRYPOINT ["java","-jar","app.jar"]
