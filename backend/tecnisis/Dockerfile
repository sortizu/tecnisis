FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY . .

# Cambia a `./gradlew build` para asegurar que todas las dependencias se instalen
RUN ./gradlew bootJar -x test

FROM openjdk:21-jdk-slim

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
