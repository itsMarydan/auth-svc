FROM openjdk:19

ENV REDIS_HOST redis
ADD build/libs/auth-svc-0.0.1-SNAPSHOT.jar auth-svc-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","auth-svc-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080