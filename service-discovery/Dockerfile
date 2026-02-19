FROM eclipse-temurin:21-jdk-ubi9-minimal

ARG JAR_FILE=./build/libs/*.jar

EXPOSE 8761

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]