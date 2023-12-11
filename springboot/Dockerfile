FROM openjdk:8-alpine
COPY target/trading*.jar /usr/local/app/springboot/lib/springboot.jar
ENTRYPOINT ["java","-jar","/usr/local/app/springboot/lib/springboot.jar"]
