FROM openjdk:8-jre-alpine3.9
WORKDIR /app

ENV POSTGRE_SQL_SERVICE=postgreSQL
EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
