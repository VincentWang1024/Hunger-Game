FROM openjdk:8-jre-alpine
#ENV HOST=localhost
COPY target/mcdJervis-0.0.1-SNAPSHOT.jar /mcdjervis.jar
#CMD ["/usr/bin/java", "-cp", "/auldfellas.jar", "service.core.Quoter"]
CMD java -jar /mcdjervis.jar service.mcdjervis.Application ${HOSTNAME}
#CMD java -jar /auldfellas.jar ${HOST}
