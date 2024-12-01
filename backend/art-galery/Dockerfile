# Usa una imagen base con Maven preinstalado
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y resuelve dependencias
COPY pom.xml .
RUN mvn dependency:resolve

# Copia el resto del proyecto y construye el paquete
COPY src ./src
RUN mvn clean package -DskipTests

# Usa una imagen más ligera para ejecutar el JAR generado
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/art-galery-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]