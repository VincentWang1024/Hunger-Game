FROM openjdk:8-jre-alpine
ENV HOST=localhost
COPY target/mcdStillorgan-0.0.1-SNAPSHOT.jar /mcdstillorgan.jar
#CMD ["/usr/bin/java", "-cp", "/dodgydrivers.jar", "service.core.Quoter"]
CMD java -jar /mcdstillorgan.jar service.mcdstillorgan.Application ${HOSTNAME}
#CMD java -jar /dodgydrivers.jar ${HOST}