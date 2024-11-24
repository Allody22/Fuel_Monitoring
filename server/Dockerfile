FROM eclipse-temurin:17

WORKDIR /application

COPY target/server-0.0.1-SNAPSHOT.jar /application/server-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/application/server-0.0.1-SNAPSHOT.jar"]
