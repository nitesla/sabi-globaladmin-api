FROM openjdk:8-jdk-alpine
MAINTAINER Spinnel consulting
EXPOSE 8080
COPY target/sabi_globaladmin-0.0.1-SNAPSHOT.jar sabi_globaladmin-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/sabi_globaladmin-0.0.1-SNAPSHOT.jar"]
