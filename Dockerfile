# /backend/Dockerfile
FROM openjdk:11-jdk-slim
VOLUME /tmp
COPY target/pokeGuide.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
