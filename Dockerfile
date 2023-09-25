FROM gradle:8.3-jdk17-jammy AS compile

WORKDIR /home/gradle

COPY build.gradle ./
COPY src src

RUN gradle clean build
RUN gradle clean assemble

FROM eclipse-temurin:17-jre-jammy

WORKDIR /opt/app
COPY --from=compile /home/gradle/build/libs/*SNAPSHOT.jar ./app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
