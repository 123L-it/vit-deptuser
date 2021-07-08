FROM openjdk:8-jre-alpine3.9
WORKDIR /app

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
