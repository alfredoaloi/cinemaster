# syntax=docker/dockerfile:1

FROM openjdk:12

WORKDIR /

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

RUN mkdir /tickets

COPY src ./src

CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql"]