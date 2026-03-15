# Stage 1: Build dependencies
FROM maven:3.9.6-amazoncorretto-17 AS deps
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Stage 2: Build application
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY --from=deps /root/.m2 /root/.m2
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 3: Runtime
FROM amazoncorretto:17-alpine
WORKDIR /app

# Argumentos de construcción
ARG MSVC_NAME=msvc-usuarios
ARG PORT=8001

# Variables de entorno
ENV SERVER_PORT=${PORT}
ENV JAVA_OPTS="-XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:+UseG1GC \
    -XX:+UseStringDeduplication \
    -Djava.security.egd=file:/dev/./urandom"

# Copiar JAR dinámicamente usando el argumento
COPY --from=build /app/target/${MSVC_NAME}-0.0.1-SNAPSHOT.jar app.jar

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
