FROM openjdk:11-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["java","-jar","/app.jar"]
