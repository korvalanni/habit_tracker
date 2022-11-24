#
# Build stage
#
FROM maven:3.8.5-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN echo $PROD_CONF > /home/app/src/main/resources/application.properties && echo $TEST_CONF > /home/app/src/test/resources/application.properties
RUN mvn -B package -e -X --file /home/app/pom.xml

#
# Package stage
#
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /home/app/target/habitstracker-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
