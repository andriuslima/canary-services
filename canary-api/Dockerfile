FROM openjdk:11-jre

WORKDIR /opt/app

ENV SPRING_PROFILES_ACTIVE container
ENV SPRING_SERVER_PORT 8080

COPY build/libs/canary-api.jar .

EXPOSE 8080

CMD java -jar canary-api.jar

