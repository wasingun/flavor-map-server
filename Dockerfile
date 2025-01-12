# OpenJDK 17 이미지 사용
FROM openjdk:17-jdk-slim

# JAR 파일을 컨테이너 내부로 복사
COPY build/libs/flavor-map-all.jar /app/flavor-map.jar

# 컨테이너 실행 명령어
CMD ["java", "-jar", "/app/flavor-map.jar"]

# 환경 변수를 사용해 실행 시 설정 파일을 선택합니다.
ENV CONFIG_FILE=application.conf

# 컨테이너 시작 시 실행할 명령어를 설정합니다.
ENTRYPOINT ["java", "-Dktor.config=/app/$CONFIG_FILE", "-jar", "/app/flavor-map.jar"]

# 컨테이너에서 사용하는 포트를 열어줌
EXPOSE 59908