FROM amazoncorretto:17
ARG JAR_FILE=service-c-0.0.1-SNAPSHOT.jar
EXPOSE 8080
USER 1000
COPY target/${JAR_FILE} /app.jar
ENV TZ "Africa/Johannesburg"
ENTRYPOINT exec java -Xms512M -Xmx512M -jar /app.jar