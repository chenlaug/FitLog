# FitLog — Backend API

A REST API for tracking fitness workouts, built with Spring Boot and secured with JWT authentication.

![CI](https://github.com/chenlaug/FitLog/actions/workflows/ci.yml/badge.svg)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4 |
| Database | PostgreSQL 16 |
| Migrations | Flyway |
| Security | Spring Security + JWT (JJWT) |
| ORM | Hibernate / Spring Data JPA |
| Build | Maven |
| Tests | JUnit 5 + Mockito + H2 |
| CI | GitHub Actions |

---

## Getting Started

### Prerequisites

- Java 25
- Docker & Docker Compose

### 1. Start the database

```bash
docker-compose up -d
```

This starts:
- **PostgreSQL** on `localhost:5432`
- **pgAdmin** on `http://localhost:8080` (admin UI)

### 2. Configure environment variables

Create a `.env` file at the root or set the following variables:

```env
DB_URL=jdbc:postgresql://localhost:5432/fitlog
DB_USERNAME=root
DB_PASSWORD=root
JWT_SECRET=your_secret_here
```

Generate a secure JWT secret with Node.js:

```bash
node -e "console.log(require('crypto').randomBytes(64).toString('hex'))"
```

### 3. Run the application

```bash
cd Back-end
./mvnw spring-boot:run
```

The API is available at `http://localhost:3000`

---

## API Endpoints

### Auth

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| `POST` | `/auth/register` | Create a new account | No |
| `POST` | `/auth/login` | Login and receive a JWT token | No |

### User

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| `GET` | `/user/me` | Get current user profile | Yes |
| `PATCH` | `/user` | Update name or email | Yes |
| `DELETE` | `/user` | Delete current account | Yes |

> For protected endpoints, add the token in the request header:
> `Authorization: Bearer <token>`

---

## Running Tests

```bash
cd Back-end
./mvnw test
```

Tests use an **H2 in-memory database** — no PostgreSQL required to run them.

| Test class | Coverage |
|---|---|
| `UserServiceTest` | createUser, getMe, deleteById, updateById |
| `AuthServiceTest` | login success, bad credentials, user not found |
| `FitLogApplicationTests` | Spring context loads correctly |

---

## pgAdmin

| | |
|---|---|
| URL | http://localhost:8080 |
| Email | root@root.com |
| Password | root |

---

## Project Structure

```
FitLog/
├── .github/
│   └── workflows/
│       └── ci.yml          # GitHub Actions — runs tests on push to master
├── Back-end/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/FitLog/
│   │   │   │   ├── auth/           # Login & register
│   │   │   │   ├── user/           # User CRUD
│   │   │   │   └── Configuration/  # Security, JWT, exception handler
│   │   │   └── resources/
│   │   │       ├── db/migration/   # Flyway SQL migrations
│   │   │       └── application.properties
│   │   └── test/
│   │       ├── java/               # Unit tests
│   │       └── resources/
│   │           └── application-test.properties  # H2 config for tests
│   └── pom.xml
└── docker-compose.yml
```
