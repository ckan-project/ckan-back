FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} datastore.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","datastore.jar"]
