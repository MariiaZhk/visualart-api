# VisualArt Service

REST API для VisualArt Application на Spring Boot з PostgreSQL.

---

## 📌 Технології

- Java 17
- Spring Boot 3
- PostgreSQL
- Docker / Docker Compose
- Maven
- Lombok
- Liquibase

---

## 🚀 Швидкий старт (Docker)

> Рекомендований спосіб запуску

1️⃣ Клонуйте репозиторій:

```bash
git clone https://github.com/MariiaZhk/visualart-api-SpringBootRestApi.git
cd visualart-api-SpringBootRestApi
```

2️⃣ Запустіть застосунок та базу даних:

```bash
docker-compose up --build
```

📌 Після запуску API доступне на:

```
http://localhost:8080
```

3️⃣ Зупинка та видалення контейнерів:

```bash
docker compose down -v
```

---

## 🛠 Альтернативний запуск (без Docker)

> Має бути встановлений PostgreSQL і створена БД `visualartdb`

1️⃣ Збірка проєкту:

```bash
mvn clean package
```

2️⃣ Запуск Spring Boot:

```bash
mvn spring-boot:run
```

---

## 📡 API Endpoints (базовий URL)

```
http://localhost:8080/api
```

### 👨‍🎨 Artists

| Метод  | Endpoint          | Опис                   |
| ------ | ----------------- | ---------------------- |
| POST   | /api/artists      | Створити артиста       |
| GET    | /api/artists      | Отримати всіх артистів |
| GET    | /api/artists/{id} | Отримати артиста за ID |
| PUT    | /api/artists/{id} | Оновити артиста        |
| DELETE | /api/artists/{id} | Видалити артиста       |

### 🖼 Artworks

| Метод  | Endpoint           | Опис                   |
| ------ | ------------------ | ---------------------- |
| POST   | /api/artworks      | Створити артворк       |
| GET    | /api/artworks      | Отримати всі артворки  |
| GET    | /api/artworks/{id} | Отримати артворк за ID |
| PUT    | /api/artworks/{id} | Оновити артворк        |
| DELETE | /api/artworks/{id} | Видалити артворк       |

---
