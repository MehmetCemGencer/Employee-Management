FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/employee-0.0.1-SNAPSHOT.jar employee-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/employee-0.0.1.jar"]