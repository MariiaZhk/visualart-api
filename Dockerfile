# Базовий образ з JDK 17
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Копіюємо JAR файл в контейнер
COPY target/visualart-service-1.0.0.jar app.jar

# Запуск JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
