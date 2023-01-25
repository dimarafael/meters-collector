FROM openjdk:19-jdk-alpine
COPY ./target/meters-collector-0.0.1-SNAPSHOT.jar /usr/src/myapp/meters-collector-0.0.1-SNAPSHOT.jar
COPY meters.mv.db /usr/src/myapp/meters.mv.db
WORKDIR /usr/src/myapp
CMD "java" "-jar" "meters-collector-0.0.1-SNAPSHOT.jar"
