FROM eclipse-temurin:17-jdk-alpine as build

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} springbootapp.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/springbootapp.jar"]