FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/trade-execution-service-*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "app.jar"]
