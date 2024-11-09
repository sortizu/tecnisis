FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

FROM openjdk:21-jdk-slim

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
