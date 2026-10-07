# Library API

Repository: [github.com/davisrr-18/library-api](https://github.com/davisrr-18/library-api) — v1 console: [library-console](https://github.com/davisrr-18/library-console)

REST API to manage book catalog, readers, and loans. Data is stored in **MySQL** (Docker Compose). Evolution of the in-memory [Library Console](../01-biblioteca-console/README.md) project.

## Stack

- Java 21
- Spring Boot 4 (Spring Web, Spring Data JPA, Bean Validation)
- MySQL 8
- Docker Compose
- Maven

## Run

### Option A — Quick start (no Docker): profile `local` (H2 in memory)

Default if you do not set `SPRING_PROFILES_ACTIVE`:

```bash
./mvnw spring-boot:run
curl -s http://localhost:8080/api/v1/health
```

Data is lost when the app stops (same idea as the console v1). Use this while fixing Docker or learning Spring Web.

### Option B — Target setup (Marco 2): profile `docker` + MySQL

**1. Fix Docker access** (if you see `permission denied` on `docker.sock`):

```bash
sudo usermod -aG docker "$USER"
newgrp docker   # or log out and back in
docker ps       # should work without sudo
```

**2. Database**

```bash
cp .env.example .env
docker compose up -d
docker compose ps   # wait until healthy
```

**3. Application** (`.env` sets `SPRING_PROFILES_ACTIVE=docker`)

```bash
set -a && source .env && set +a
./mvnw spring-boot:run
```

MySQL in Docker uses **host port 3307** so it does not clash with a system MySQL on `3306`.

**IDE:** use launch config **Library API (local H2)** or **Library API (Docker MySQL)** in `.vscode/launch.json`.

Expected health response: `{"status":"UP"}`

## Troubleshooting

| Symptom | Likely cause | What to do |
|---------|----------------|------------|
| `permission denied` on `docker.sock` | User not in `docker` group | `sudo usermod -aG docker "$USER"` then re-login or `newgrp docker` |
| `Communications link failure` on port **3307** | Profile `docker` but container not running | `docker compose up -d` or run with profile `local` until Docker works |
| `Access denied for user 'library'@'localhost'` | App hit **system** MySQL on 3306 | Use profile `docker` + port **3307**, not 3306 |
| Wrong password after changing `.env` | Old Docker volume | `docker compose down -v` then `docker compose up -d` (wipes DB data) |

## Project layout

```
src/main/java/com/davisrr/libraryapi/
  LibraryApiApplication.java   # Bootstrap
  controller/                  # REST adapters
  service/                     # Business rules
  repository/                  # JPA persistence
  entity/                      # Database model (not exposed in JSON)
  dto/                         # API request/response contracts
  exception/                   # Domain errors (+ global handler later)
src/main/resources/
  application.yml
docker-compose.yml
```

## Architecture

| Package / layer | Role | Console v1 analogue |
|-----------------|------|---------------------|
| `LibraryApiApplication` | Starts Spring Boot | `library.app` |
| `controller` | HTTP, status codes, DTOs | `library.controller` (no menu) |
| `service` | Business rules, transactions | `library.service` |
| `repository` | MySQL via JPA | in-memory maps |
| `entity` | Persisted model | `library.entities` |
| `dto` | JSON in/out | replaces hand-built `serializer` |
| `exception` | Domain failures | `library.exceptions` |

**Dependency rules**

- `controller` → `service` and **DTOs** only (do not expose `entity` in controllers).
- `service` → `repository`, `entity`, `exception`; maps entity ↔ DTO.
- `repository` → `entity` only.
- `dto` → no JPA dependencies.
- `entity` → no Spring Web dependencies.

## Features

- [x] Project scaffold (Maven, Spring Boot, MySQL via Compose)
- [x] `GET /api/v1/health`
- [ ] Book registration (auto ID, required title/author, no duplicate title+author)
- [ ] List books
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
| Health | `GET /health` | Done |
| Register book | `POST /books` | Planned |
| List books | `GET /books` | Planned |
| Search / filter | `GET /books?search=` / `?available=true` | Planned |
| Register reader | `POST /readers` | Planned |
| Get reader | `GET /readers/{id}` | Planned |
| List readers | `GET /readers` | Planned |
| Loan | `POST /loans` | Planned |
| Return | `POST /loans/returns` | Planned |

## Planned enhancements

- JWT authentication (Marco 3)
- Flyway migrations
- Global error handling (`@ControllerAdvice`)
- Automated tests (JUnit 5, Mockito)
- Docker image for the Java app
