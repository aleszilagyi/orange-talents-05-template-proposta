# syntax=docker/dockerfile:1
FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER Alexandre Szilagyi Santos
COPY target/proposta-0.0.1.jar proposta.jar
ENTRYPOINT ["java", "-jar", "/proposta.jar"]
EXPOSE 8080