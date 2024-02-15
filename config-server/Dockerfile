FROM eclipse-temurin:17-jdk-alpine
RUN apk update; apk --no-cache add iputils curl busybox-extras
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} config-server.jar
ENTRYPOINT ["java","-jar","/config-server.jar"]
LABEL authors="Quang_Tien"