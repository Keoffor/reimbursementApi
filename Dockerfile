FROM openjdk:11 as base
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw install

FROM tomcat:9
WORKDIR /webapp
COPY --from=base /app/target/reimbursementApi-0.0.1-SNAPSHOT.jar .
RUN rm -rf ROOT && mv reimbursementApi-0.0.1-SNAPSHOT.jar ROOT.jar
# FROM openjdk:8-jdk-alpine
# ARG JAR_FILE=target/*.jar
# COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java", "-jar", "/app.jar"]