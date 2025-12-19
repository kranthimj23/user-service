# User Service

User management microservice for the Mobile Banking Platform. Handles user registration, profile management, KYC status tracking, and user status history.

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Flyway for database migrations
- Micrometer for metrics

## Features

- User profile creation and management
- KYC (Know Your Customer) status tracking
- User status management (ACTIVE, INACTIVE, SUSPENDED, CLOSED)
- Status history tracking
- Profile preferences management

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/v1/users | Create a new user |
| GET | /api/v1/users/{id} | Get user by ID |
| GET | /api/v1/users/auth/{authId} | Get user by auth ID |
| PUT | /api/v1/users/{id} | Update user |
| DELETE | /api/v1/users/{id} | Delete user |
| GET | /api/v1/users/{id}/profile | Get user profile |
| PUT | /api/v1/users/{id}/profile | Update user profile |
| PUT | /api/v1/users/{id}/status | Update user status |
| GET | /api/v1/users/{id}/status/history | Get status history |
| GET | /actuator/health | Health check endpoint |
| GET | /actuator/prometheus | Prometheus metrics |

## Project Structure

```
user-service/
├── src/
│   └── main/
│       ├── java/com/mobilebanking/user/
│       │   ├── config/          # Web and OpenAPI configuration
│       │   ├── controller/      # REST controllers
│       │   ├── dto/             # Data transfer objects
│       │   ├── entity/          # JPA entities
│       │   ├── exception/       # Exception handling
│       │   ├── repository/      # JPA repositories
│       │   └── service/         # Business logic
│       └── resources/
│           ├── application.yml
│           ├── application-dev.yml
│           ├── application-prod.yml
│           └── db/migration/    # Flyway migrations
├── helm/                        # Helm chart for Kubernetes deployment
├── Dockerfile                   # Multi-stage Docker build
├── Jenkinsfile                  # CI/CD pipeline
└── pom.xml                      # Maven dependencies
```

## Local Development

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- Docker (optional)

### Running Locally

1. Start PostgreSQL:
```bash
docker run -d --name postgres-user \
  -e POSTGRES_DB=user_db \
  -e POSTGRES_USER=user_user \
  -e POSTGRES_PASSWORD=user_password \
  -p 5433:5432 postgres:14
```

2. Run the application:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

3. Access Swagger UI: http://localhost:8082/swagger-ui.html

### Building Docker Image

```bash
docker build -t user-service:latest .
```

## Kubernetes Deployment

### Using Helm

```bash
# Development
helm install user-service ./helm -f ./helm/values-dev.yaml -n mobile-banking-dev

# Staging
helm install user-service ./helm -f ./helm/values-staging.yaml -n mobile-banking-staging

# Production
helm install user-service ./helm -f ./helm/values-prod.yaml -n mobile-banking-prod
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| SPRING_DATASOURCE_URL | PostgreSQL connection URL | jdbc:postgresql://localhost:5433/user_db |
| SPRING_DATASOURCE_USERNAME | Database username | user_user |
| SPRING_DATASOURCE_PASSWORD | Database password | - |
| AUTH_SERVICE_URL | Auth service URL for validation | http://auth-service:8081 |

## Data Models

### User Status
- PENDING_VERIFICATION
- ACTIVE
- INACTIVE
- SUSPENDED
- CLOSED

### KYC Status
- NOT_STARTED
- PENDING
- IN_REVIEW
- VERIFIED
- REJECTED

## Testing

```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify -P integration-tests
```

## CI/CD Pipeline

The Jenkinsfile includes the following stages:
1. Build - Compile and package
2. Unit Tests - Run unit tests
3. Integration Tests - Run integration tests
4. Code Quality - SonarQube analysis
5. Docker Build - Build container image
6. Docker Push - Push to registry
7. Helm Lint - Validate Helm chart
8. Deploy - Deploy to Kubernetes
9. Smoke Tests - Verify deployment

## Related Services

- [Auth Service](https://github.com/kranthimj23/auth-service) - Authentication and JWT tokens
- [API Gateway](https://github.com/kranthimj23/api-gateway) - Request routing and rate limiting
- [Infrastructure](https://github.com/kranthimj23/mobile-banking-infra) - Terraform and observability
