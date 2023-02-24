FROM openjdk:19
ADD build/libs/auth-svc-0.0.1-SNAPSHOT.jar auth-svc-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","auth-svc-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080