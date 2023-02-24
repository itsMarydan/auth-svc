FROM gradle:jdk19 AS builder

WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY src /app/src

RUN ./gradlew build

FROM openjdk:19

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

RUN apt-get update && apt-get install -y postgresql-client

CMD ["java", "-jar", "app.jar"]
