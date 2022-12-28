FROM openjdk:8-jdk-alpine
# limit container privelages
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ADD ./build/libs/api-demo-0.0.1-SNAPSHOT.jar .
COPY ./build/libs/api-demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]