FROM eclipse-temurin:23-jre-alpine
WORKDIR /app
# Копіюємо зібраний jar файл
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]