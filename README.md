# 🎨 VisualArt Service

A REST API for managing artists and their artworks — powered by Spring Boot and PostgreSQL.
Bring your art data to life and explore it programmatically!

---

## 📌 Technologies

- Java 17
- Spring Boot 3
- Spring Data JPA
- Lombok
- Validation (jakarta.validation)
- PostgreSQL
- Liquibase
- Maven
- JUnit 5 + Spring Boot Test for integration testing
- Swagger for API documentation

---

## 🚀 Running the Application with Docker

1. Start the PostgreSQL container:

```bash
docker-compose up -d
```

2. Run the application with PostgreSQL settings (prod profile):

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

> By default, the app uses an H2 database. To connect to PostgreSQL, always use the `prod` profile.

---

## 🌐 Base URL & Swagger

- Base URL: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

---

## 👨‍🎨 ArtistController — /api/artists

| Method | Endpoint          | Description         |
| ------ | ----------------- | ------------------- |
| POST   | /api/artists      | Create a new artist |
| GET    | /api/artists      | Get all artists     |
| GET    | /api/artists/{id} | Get artist by ID    |
| PUT    | /api/artists/{id} | Update artist       |
| DELETE | /api/artists/{id} | Delete artist       |

---

## 🖼 ArtworkController — /api/artworks

| Method | Endpoint               | Description                           |
| ------ | ---------------------- | ------------------------------------- |
| POST   | /api/artworks          | Create a new artwork                  |
| GET    | /api/artworks/{id}     | Get artwork by ID                     |
| POST   | /api/artworks/\_list   | Paginated & filtered list of artworks |
| PUT    | /api/artworks/{id}     | Update artwork                        |
| DELETE | /api/artworks/{id}     | Delete artwork                        |
| POST   | /api/artworks/\_report | Generate a CSV report                 |
| POST   | /api/artworks/upload   | Upload artworks from a JSON file      |

> 📦 Sample Data
> Sample data for Artists and Artworks is available.  
> To test Artworks endpoints, first upload `upload.json` (located alongside this README) via the `/api/artworks/upload` endpoint.  
> The JSON file will populate the database with initial artworks. Use Swagger or Postman to interact with the uploaded data.

---

## 🧪 Testing

- Run all unit and integration tests via Maven:

```bash
mvn clean test
```

- Tests use an H2 in-memory database, independent of PostgreSQL.

---

## ⚡ Notes

- Liquibase automatically applies database changes on application startup.
- Use Swagger or Postman to explore and verify data in PostgreSQL.
- To start with a clean database, stop Docker and remove containers:

```bash
docker-compose down
```
