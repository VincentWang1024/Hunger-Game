FROM openjdk:8-jre-alpine
#ENV HOST=localhost
COPY target/auldfellas-0.0.1-SNAPSHOT.jar /auldfellas.jar
#CMD ["/usr/bin/java", "-cp", "/auldfellas.jar", "service.core.Quoter"]
CMD java -jar /auldfellas.jar service.auldfellas.Application ${HOSTNAME}
#CMD java -jar /auldfellas.jar ${HOST}
