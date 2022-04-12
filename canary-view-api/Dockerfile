FROM openjdk:11-jre

WORKDIR /opt/app

ENV SPRING_PROFILES_ACTIVE container

COPY build/libs/canary-view-api.jar .

EXPOSE 8082

CMD java -jar canary-view-api.jar

