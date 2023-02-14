FROM openjdk:19-jdk-alpine
COPY meters-collector-0.0.1.jar /usr/src/myapp/meters-collector.jar
WORKDIR /usr/src/myapp
EXPOSE 80/tcp
VOLUME /var/log/spring
CMD "java" "-jar" "meters-collector.jar"

