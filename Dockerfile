# Java 21 기반의 경량 Alpine 이미지 사용
FROM eclipse-temurin:21-jdk-alpine

# 작업 디렉토리 생성
WORKDIR /app

# build/libs 폴더의 JAR 파일 복사 (gradlew build 후 생성됨)
COPY build/libs/*.jar app.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
