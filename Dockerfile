FROM gradle:jdk19 AS builder

ENV SPRING_PROFILES_ACTIVE=dev

WORKDIR /app

#COPY build.gradle settings.gradle gradlew /app/

#COPY src /app/src

COPY --chown=gradle:gradle . /app


RUN ./gradlew build --no-daemon


FROM openjdk:19

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]

EXPOSE 8080