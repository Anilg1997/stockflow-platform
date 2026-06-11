FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/audit-service-*.jar app.jar
EXPOSE 8101
ENTRYPOINT ["java", "-jar", "app.jar"]
