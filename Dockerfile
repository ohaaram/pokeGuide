# 베이스 이미지로 OpenJDK 사용
FROM openjdk:11-jre-slim

# JAR 파일을 Docker 이미지로 복사
COPY target/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
