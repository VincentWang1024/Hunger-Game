FROM openjdk:8-jre-alpine
ENV HOST=localhost
COPY target/bgkGrafton-0.0.1-SNAPSHOT.jar /bgkgrafton.jar
#CMD ["/usr/bin/java", "-cp", "/girlpower.jar", "service.core.Quoter"]
CMD java -jar /bgkgrafton.jar service.bgkgrafton.Application ${HOSTNAME}
#CMD java -jar /girlpower.jar  ${HOST}