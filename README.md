# Library API

Repository: [github.com/davisrr-18/library-api](https://github.com/davisrr-18/library-api)

REST API to manage book catalog, readers, and loans. Data is persisted in **MySQL**. This project is the HTTP counterpart to the [Library Console](https://github.com/davisrr-18/library-console) CLI application.

## Stack

- Java 21
- Spring Boot 4 (Spring Web, Spring Data JPA, Bean Validation)
- MySQL 8
- Docker Compose
- Maven

## Run

### MySQL (recommended)

```bash
cp .env.example .env
docker compose up -d
set -a && source .env && set +a
./mvnw spring-boot:run
```

MySQL is exposed on host port **3307** by default to avoid conflicting with a local MySQL instance on `3306`.

### Local development (H2, in-memory)

```bash
./mvnw spring-boot:run
```

Uses Spring profile `local` by default. Data is cleared when the application stops.

### Smoke test

```bash
curl -s http://localhost:8080/api/v1/health
```

Expected: `{"status":"UP"}`

Copy `.env.example` to `.env` for MySQL credentials and profile settings. Do not commit `.env`.

## Project layout

```
src/main/java/com/davisrr/libraryapi/
  LibraryApiApplication.java   # Application entry point
  controller/                  # REST endpoints
  service/                     # Business rules
  repository/                  # JPA repositories
  entity/                      # Persistence model (not exposed in JSON)
  dto/                         # Request and response contracts
  exception/                   # Domain errors
src/main/resources/
  application.yml
  application-docker.yml
  application-local.yml
docker-compose.yml
```

## Architecture

| Layer | Responsibility |
|-------|----------------|
| `controller` | HTTP mapping, validation, status codes, DTOs |
| `service` | Business rules and orchestration |
| `repository` | Database access via Spring Data JPA |
| `entity` | Relational model |
| `dto` | API input/output (JSON) |
| `exception` | Domain-specific failures |

**Dependency rules**

- `controller` → `service` and DTOs only (entities are not returned from controllers).
- `service` → `repository`, `entity`, `exception`; maps between entity and DTO.
- `repository` → `entity` only.
- `dto` → no JPA dependencies.
- `entity` → no Spring Web dependencies.

## Features

- [x] Health check (`GET /api/v1/health`)
- [x] MySQL via Docker Compose; optional H2 profile for local runs
- [x] Book registration (auto ID, required title/author, no duplicate title+author)
- [x] List books
- [ ] Search books by title or author
- [ ] List available books
- [ ] Reader registration and lookup
- [ ] List readers
- [ ] Loan and return
- [ ] Domain exceptions mapped to HTTP status codes

## Business rules

- Each book gets a sequential numeric id.
- Two books cannot share the same title **and** author (case-insensitive).
- Readers have sequential id and name; duplicate names are not allowed (case-insensitive).
- A loan links a book to a reader while the copy is unavailable (`available` + `borrowedReaderId` on the book).

## API overview

Base path: `/api/v1`

| Use case | Method and path | Status |
|----------|-----------------|--------|
| Health | `GET /health` | Available |
| Register book | `POST /books` | Available |
| List books | `GET /books` | Available |
| Search / filter | `GET /books?search=` / `?available=true` | Planned |
| Register reader | `POST /readers` | Planned |
| Get reader | `GET /readers/{id}` | Planned |
| List readers | `GET /readers` | Planned |
| Loan | `POST /loans` | Planned |
| Return | `POST /loans/returns` | Planned |

## Planned enhancements

- JWT authentication and authorization
- Flyway database migrations
- Global error handling (`@ControllerAdvice`)
- Automated tests (JUnit 5, Mockito)
- Container image for the application
