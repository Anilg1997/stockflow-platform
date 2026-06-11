FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/config-server-*.jar app.jar
COPY config-repo/ /app/config-repo/
EXPOSE 8888
ENV CONFIG_REPO_PATH=/app/config-repo
ENTRYPOINT ["java", "-jar", "app.jar"]
