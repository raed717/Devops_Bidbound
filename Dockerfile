FROM openjdk:11
WORKDIR /app
COPY ./ /app/
RUN mvn clean package
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "target/DevOps_Project-2.1.jar"]
