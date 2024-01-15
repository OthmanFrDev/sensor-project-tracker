FROM openjdk:17-jdk-slim

ARG JAR_FILE=build/libs/project-tracker-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} project-tracker-api.jar

ENTRYPOINT ["java","-jar","/project-tracker-api.jar"]

EXPOSE 8080
