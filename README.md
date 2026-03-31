# FinancialFlow

A RESTful API for personal financial management built with Spring Boot. This application allows users to track transactions, manage categories, generate PDF reports, and export data to Excel.

---

## Table of Contents

- [Technologies](#technologies)
- [Architecture](#architecture)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Running with Docker](#running-with-docker)
  - [Running Locally](#running-locally)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Security](#security)

---

## Technologies

| Category | Technology |
|----------|------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.3.5 |
| **Data Access** | Spring Data JPA |
| **Security** | Spring Security, JWT (Auth0) |
| **Database** | PostgreSQL 16 |
| **Documentation** | OpenAPI (Swagger) |
| **PDF Generation** | OpenPDF |
| **Excel Export** | Apache POI |
| **Build Tool** | Maven 3.9.6 |
| **Containerization** | Docker |

---

## Architecture

The project follows a **Layered Architecture** implementing the **MVC (Model-View-Controller)** pattern:

```
┌─────────────────────────────────────────┐
│           Controllers Layer            │  ← REST endpoints, request validation
├─────────────────────────────────────────┤
│            Services Layer              │  ← Business logic, orchestration
├─────────────────────────────────────────┤
│           Repository Layer             │  ← Data access, JPA repositories
├─────────────────────────────────────────┤
│             Models Layer               │  ← Entities (User, Transaction, Category)
├─────────────────────────────────────────┤
│              DTOs/Mappers              │  ← Data transfer, object mapping
└─────────────────────────────────────────┘
```

### Design Patterns Used

- **Dependency Injection**: Spring IoC container manages component lifecycle
- **DTO Pattern**: Separates internal models from API contracts
- **Repository Pattern**: Abstracts data access logic
- **Mapper Pattern**: Converts between entities and DTOs
- **JWT Authentication**: Stateless security with JSON Web Tokens

---

## Features

- **User Management**: Registration, authentication, and profile management
- **Category Management**: CRUD operations for transaction categories
- **Transaction Tracking**: Record income and expenses with categorization
- **PDF Reports**: Generate downloadable PDF summaries
- **Excel Export**: Export transaction data to Excel (.xlsx) format
- **API Documentation**: Interactive Swagger UI for API exploration
- **Security**: JWT-based authentication and authorization

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.9+
- Docker (optional, for containerized deployment)
- PostgreSQL 16 (if running locally without Docker)

---

### Running with Docker

The easiest way to run the application is using Docker Compose, which sets up both the application and PostgreSQL database.

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd financialflow
   ```

2. Build and start the containers:
   ```bash
   docker-compose up -d
   ```

3. The application will be available at:
   - **API**: http://localhost:8080
   - **Swagger UI**: http://localhost:8080/swagger-ui.html

4. To stop the services:
   ```bash
   docker-compose down
   ```

5. To stop and remove data volumes:
   ```bash
   docker-compose down -v
   ```

---

### Running Locally

1. Start a PostgreSQL instance (using Docker or local installation):
   ```bash
   docker run -d \
     --name postgres \
     -e POSTGRES_DB=financialflow \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=secret \
     -p 5432:5432 \
     postgres:16
   ```

2. Configure environment variables or update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/financialflow
   spring.datasource.username=postgres
   spring.datasource.password=secret
   ```

3. Build the application:
   ```bash
   ./mvnw clean package -DskipTests
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or:
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

5. Access the application:
   - **API**: http://localhost:8080
   - **Swagger UI**: http://localhost:8080/swagger-ui.html

---

## API Documentation

Once the application is running, interactive API documentation is available via **Swagger UI**:

- **URL**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

The documentation includes all endpoints, request/response schemas, and authentication requirements.

---

## Project Structure

```
financialflow/
├── src/main/java/com/financialflow/
│   ├── config/           # Configuration classes
│   ├── controllers/      # REST API controllers
│   │   ├── AuthController.java
│   │   ├── CategoryController.java
│   │   ├── ExcelController.java
│   │   ├── PdfController.java
│   │   ├── TransactionController.java
│   │   └── UserDataController.java
│   ├── dto/              # Data Transfer Objects
│   ├── mapper/           # Entity-DTO mappers
│   ├── models/           # JPA Entities
│   │   ├── Category.java
│   │   ├── Transaction.java
│   │   ├── TransactionType.java (Enum)
│   │   └── UserData.java
│   ├── repository/       # Spring Data repositories
│   ├── security/         # JWT and security config
│   │   ├── Auth.java
│   │   └── Security.java
│   ├── services/         # Business logic
│   │   ├── AuthService.java
│   │   ├── CategoryService.java
│   │   ├── GenerateFilesService.java
│   │   ├── TransactionService.java
│   │   └── UserDataService.java
│   └── utils/            # Utility classes
├── src/main/resources/
│   └── application.properties
├── Dockerfile            # Docker build configuration
├── docker-compose.yml    # Multi-container orchestration
├── pom.xml              # Maven dependencies
└── README.md            # This file
```

---

## Security

The application uses **JWT (JSON Web Tokens)** for stateless authentication:

- **Token Format**: Bearer tokens in Authorization header
- **Algorithm**: HS256 (HMAC with SHA-256)
- **Token Provider**: Auth0 Java JWT library

### Authentication Flow

1. User registers/logs in via `/auth/**` endpoints
2. Server validates credentials and returns a JWT
3. Client includes JWT in `Authorization: Bearer <token>` header
4. Server validates JWT on protected endpoints

### Protected Endpoints

Most endpoints require authentication. Unsecured endpoints include:
- `POST /auth/login`
- `POST /auth/register`

