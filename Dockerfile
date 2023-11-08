FROM maven:3.9.5-openjdk-11-slim

WORKDIR /app
COPY ./ /app/
RUN mvn clean package
EXPOSE 8085
CMD ["java", "-jar", "target/DevOps_Project-2.1.jar"]