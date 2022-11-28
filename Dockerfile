FROM adoptopenjdk/openjdk11:jre-11.0.12_7-alpine

COPY target/*.jar /app.jar

CMD java -jar app.jar