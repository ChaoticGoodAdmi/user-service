FROM openjdk:17-jre-slim
VOLUME /tmp
COPY target/user-service.jar user-service.jar
ENTRYPOINT ["java", "-jar", "/user-service.jar"]