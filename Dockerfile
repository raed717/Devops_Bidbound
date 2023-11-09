FROM openjdk:11
EXPOSE 8085
ADD target/DevOps_Project-2.1.jar DevOps_Project-2.1.jar
ENTRYPOINT ["java", "-jar", "DevOps_Project-2.1.jar"]
LABEL release="devops-proj"
ENV IMAGE_NAME="iheb-image"