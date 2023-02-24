FROM gradle:jdk19 AS builder

ENV SPRING_PROFILES_ACTIVE=dev

WORKDIR /app

COPY . /app


RUN ./gradlew build --no-daemon


FROM openjdk:19

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]

EXPOSE 8080