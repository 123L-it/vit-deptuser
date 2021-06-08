FROM openjdk:8-jre-alpine3.9
RUN mkdir -p /app
WORKDIR /app

COPY ./build/libs/*SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
