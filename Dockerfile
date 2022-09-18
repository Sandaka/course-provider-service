FROM maven:3.6.1-jdk-8-alpine AS build
RUN mkdir -p /workspace3
WORKDIR /workspace3
COPY pom.xml /workspace3
COPY src /workspace3/src
RUN mvn -f pom.xml clean package
#RUN mvn clean install

FROM openjdk:8-alpine
COPY --from=build /workspace3/target/*.jar course-provider-service-1.0.0-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","course-provider-service-1.0.0-SNAPSHOT.jar"]


#FROM java:8
#FROM maven:alpine
#
## image layer
#WORKDIR /app
#ADD pom.xml /app
#RUN mvn verify clean --fail-never
#
## Image layer: with the application
#COPY . /app
#RUN mvn -v
#RUN mvn clean install -DskipTests
#EXPOSE 8081
#ADD ./target/course-provider-service-1.0.0-SNAPSHOT.jar /developments/
#ENTRYPOINT ["java","-jar","/developments/course-provider-service-1.0.0-SNAPSHOT.jar"]




#FROM maven:3.6-jdk-8-alpine as build
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -f pom.xml clean install

#FROM openjdk:8
#VOLUME /tmp
##RUN mvn clean install
#ADD target/cloud-eureka-discovery-service-1.0.0-SNAPSHOT.jar cloud-eureka-discovery-service-1.0.0-SNAPSHOT.jar
#EXPOSE 8083
#ENTRYPOINT ["java","-jar","cloud-eureka-discovery-service-1.0.0-SNAPSHOT.jar"]