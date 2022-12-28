FROM openjdk:8-jre-alpine
#ENV HOST=localhost
COPY target/BgkFranchise-0.0.1-SNAPSHOT.jar  /bgkfranchise.jar
#CMD ["/usr/bin/java", "-cp", "/broker.jar", "service.core.Broker"]
#EXPOSE 3000
CMD java -jar /bgkfranchise.jar service.bgkfranchise.Application ${HOSTNAME}
#CMD java -jar /broker.jar ${HOST}
