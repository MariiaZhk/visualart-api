# VisualArt Service

REST API для VisualArt Application на Spring Boot з PostgreSQL.

## Технології

- Java 17
- Spring Boot 3
- PostgreSQL
- Docker / Docker Compose
- Maven
- Lombok

## Швидкий старт

1. Клонуйте репозиторій:

```bash
git clone https://github.com/MariiaZhk/visualart-api
cd visualart-api-SpringBootRestApi
```

2. Збудуйте JAR:

```bash
mvn clean package
```

3. Запустіть Docker Compose:

```bash
docker compose up --build
```

- **API:** `http://localhost:8080`

4. Зупинка і видалення контейнерів:

```bash
docker compose down
```
