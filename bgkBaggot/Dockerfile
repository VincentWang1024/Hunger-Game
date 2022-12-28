FROM openjdk:8-jre-alpine
ENV HOST=localhost
COPY target/bgkBaggot-0.0.1-SNAPSHOT.jar /bgkbaggot.jar
#CMD ["/usr/bin/java", "-cp", "/girlpower.jar", "service.core.Quoter"]
CMD java -jar /bgkbaggot.jar service.bgkbaggot.Application ${HOSTNAME}
#CMD java -jar /girlpower.jar  ${HOST}