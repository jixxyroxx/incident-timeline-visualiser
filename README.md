\# Tool-92 Incident Timeline Visualiser



A Spring Boot REST API for managing and visualising incidents with JWT authentication, Redis caching, and role-based access control.



\## Tech Stack



\- Java 17

\- Spring Boot 3.x

\- PostgreSQL

\- Redis

\- Flyway

\- Spring Security + JWT

\- Thymeleaf (email templates)

\- Swagger/OpenAPI

\- Docker



\## Project Structure

\## Getting Started



\### Prerequisites

\- Java 17

\- PostgreSQL running on port 5432

\- Redis running on port 6379



\### Setup



1\. Clone the repository

```bash

git clone https://github.com/jixxyroxx/incident-timeline-visualiser.git

cd incident-timeline-visualiser

```



2\. Copy environment variables

```bash

cp .env.example .env

```



3\. Update `.env` with your actual values



4\. Run the application

```bash

mvn spring-boot:run

```



\### Run with Docker



```bash

docker-compose up --build

```



\## API Endpoints



| Method | Endpoint | Description | Auth |

|--------|----------|-------------|------|

| POST | /api/auth/register | Register user | No |

| POST | /api/auth/login | Login and get JWT | No |

| GET | /api/incidents | Get all incidents | Yes |

| GET | /api/incidents/{id} | Get incident by ID | Yes |

| POST | /api/incidents | Create incident | Yes |

| PUT | /api/incidents/{id} | Update incident | Yes |

| DELETE | /api/incidents/{id} | Delete incident | Yes |

| GET | /api/incidents/status/{status} | Filter by status | Yes |

| GET | /api/users | Get all users | Yes |



\## Swagger UI



Once running, visit: `http://localhost:8080/swagger-ui.html`



\## Running Tests



```bash

mvn test

```



11 tests — all passing.



\## Developer



\*\*Vyshnavi Keerthi D\*\* — Java Developer 1

