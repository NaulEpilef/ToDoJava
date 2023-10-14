FROM ubuntu:latest as build

RUN apt-get upddate
RUN apt-get install openjdk-17-jdk -v

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

COPY --from=build /target/todolist-1.0.0.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]