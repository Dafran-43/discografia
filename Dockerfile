# ==========================
# Stage 1: Compilación
# ==========================
FROM gradle:8.7-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle clean build --no-daemon

# ==========================
# Stage 2: Ejecución
# ==========================
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/discografia-1.war app.war

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.war"]