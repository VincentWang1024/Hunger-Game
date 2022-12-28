FROM openjdk:8-jre-alpine
#ENV HOST=localhost
COPY target/broker-0.0.1-SNAPSHOT.jar  /broker.jar
#CMD ["/usr/bin/java", "-cp", "/broker.jar", "service.core.Broker"]
#EXPOSE 3000
CMD java -jar /broker.jar service.broker.Application ${HOSTNAME}
#CMD java -jar /broker.jar ${HOST}
