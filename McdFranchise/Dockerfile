FROM openjdk:8-jre-alpine
#ENV HOST=localhost
COPY target/McdFranchise-0.0.1-SNAPSHOT.jar  /mcdfranchise.jar
#CMD ["/usr/bin/java", "-cp", "/broker.jar", "service.core.Broker"]
#EXPOSE 3000
CMD java -jar /mcdfranchise.jar service.mcdfranchise.Application ${HOSTNAME}
#CMD java -jar /broker.jar ${HOST}
