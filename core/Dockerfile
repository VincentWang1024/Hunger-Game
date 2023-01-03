FROM openjdk:8-jre-alpine
COPY target/core-1.0-SNAPSHOT.jar /core.jar
ENTRYPOINT ["java","-cp","/core.jar"]