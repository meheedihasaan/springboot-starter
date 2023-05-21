FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/springboot-starter-0.0.1-SNAPSHOT.jar starter.jar
ENTRYPOINT ["java","-jar","/starter.jar"]
