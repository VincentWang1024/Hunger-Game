FROM openjdk:8-jre-alpine
ENV HOST=localhost
COPY target/mcdTempleBar-0.0.1-SNAPSHOT.jar /mcdtemplebar.jar
#CMD ["/usr/bin/java", "-cp", "/girlpower.jar", "service.core.Quoter"]
CMD java -jar /mcdtemplebar.jar service.mcdtemplebar.Application ${HOSTNAME}
#CMD java -jar /girlpower.jar  ${HOST}