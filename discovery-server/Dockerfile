# Faz 1: Maven ile Build
FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests

# Faz 2: JAR dosyasını çalıştıran imaj
FROM openjdk:17-jdk
COPY --from=build /app/target/discovery-server-0.0.1-SNAPSHOT.jar /app/discovery-server.jar
CMD java -jar /app/discovery-server.jar
