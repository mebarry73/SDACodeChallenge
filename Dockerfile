FROM openjdk:17-jdk-alpine
LABEL org.opencontainers.image.authors="barry.grotjahn"
COPY target/SDACodeChallenge-0.0.1-SNAPSHOT.jar SDACodeChallenge.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/SDACodeChallenge.jar"]