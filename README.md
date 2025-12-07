# VisualArt Service

REST API for the VisualArt application built with Spring Boot and PostgreSQL.

---

## 📌 Technologies

- Java 17
- Spring Boot 3
- PostgreSQL
- Maven
- Lombok
- Liquibase

---

## 🚀 How to Run

> Make sure PostgreSQL is installed and a database called `visualartdb` exists.

### Clone the repository

git clone https://github.com/MariiaZhk/visualart-api-SpringBootRestApi.git  
cd visualart-api-SpringBootRestApi

### Build the project

mvn clean package

### Run the application

mvn spring-boot:run

---

## 🌐 Access URLs

Base URL:

http://localhost:8080

Swagger UI:

http://localhost:8080/swagger-ui.html

---

## 📡 API Base Path

http://localhost:8080/api

---

## 👨‍🎨 Artists Endpoints

| Method | Endpoint          | Description         |
| ------ | ----------------- | ------------------- |
| POST   | /api/artists      | Create a new artist |
| GET    | /api/artists      | Get all artists     |
| GET    | /api/artists/{id} | Get artist by ID    |
| PUT    | /api/artists/{id} | Update artist       |
| DELETE | /api/artists/{id} | Delete artist       |

---

## 🖼 Artworks Endpoints

| Method | Endpoint           | Description          |
| ------ | ------------------ | -------------------- |
| POST   | /api/artworks      | Create a new artwork |
| GET    | /api/artworks      | Get all artworks     |
| GET    | /api/artworks/{id} | Get artwork by ID    |
| PUT    | /api/artworks/{id} | Update artwork       |
| DELETE | /api/artworks/{id} | Delete artwork       |

---

## 🧪 Notes

This project uses Liquibase for database migrations and PostgreSQL as the main database.  
You can test all endpoints using Swagger UI.
