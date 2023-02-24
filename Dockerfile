#FROM openjdk:19
#
#ENV REDIS_HOST redis
#ADD build/libs/auth-svc-0.0.1-SNAPSHOT.jar auth-svc-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java", "-jar","auth-svc-0.0.1-SNAPSHOT.jar"]
#EXPOSE 8080

FROM gradle:jdk19 AS builder

ENV SPRING_PROFILES_ACTIVE=dev

WORKDIR /app

#COPY build.gradle settings.gradle gradlew /app/
#
#COPY src /app/src

copy . /app


RUN gradle build

FROM openjdk:19

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]

EXPOSE 8080