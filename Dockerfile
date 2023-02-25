FROM openjdk:17
MAINTAINER maleksandrowicz93
VOLUME /tmp
EXPOSE 8000
ADD target/cqrs-demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]