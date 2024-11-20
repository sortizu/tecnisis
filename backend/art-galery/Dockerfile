# Usa una imagen base con Maven preinstalado
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y resolver las dependencias
COPY pom.xml .
RUN mvn dependency:resolve

# Copiar el resto del proyecto y construir la aplicación
COPY src ./src
RUN mvn clean package -DskipTests

# Usa una imagen más ligera para ejecutar el JAR generado
FROM eclipse-temurin:17-jre

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa anterior
COPY --from=build /app/target/art-galery-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
