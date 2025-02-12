FROM eclipse-temurin:23

LABEL org.opencontainers.image.authors="spoofy.com"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:UseSVE=0", "-jar", "/app.jar"]