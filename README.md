#  Deals Warehouse Application

A Spring Boot application to manage and persist Foreign Exchange  deals in a data warehouse environment.

---

## Features

- Accepts and persists FX deal details into a PostgreSQL database  
- Validates deal request structure including currency codes and business rules  
- Prevents duplicate deal imports based on unique Deal IDs  
- No rollback policy — all valid rows are persisted even if some fail  
- Comprehensive error handling with custom exceptions and global handler  
- Detailed logging using SLF4J  
- Unit testing with high coverage on services and validation  
- Fully Dockerized deployment for easy setup and consistency  
- Clean architecture with modular structure for maintainability

---

## Technologies Used

- **Java 21**: Core programming language  
- **Spring Boot 3.4.2**: Application framework  
- **PostgreSQL**: Relational database  
- **Maven**: Build and dependency management  
- **Docker & Docker Compose**: Containerization and orchestration  
- **Lombok**: Reduces boilerplate code (getters/setters, constructors)  
- **MapStruct**: DTO <-> Entity mapping  
- **JUnit 5 & Mockito**: Testing framework and mocking  
- **SLF4J**: Logging facade  
- **Jakarta Validation**: Bean validation annotations  
- **Spring Data JPA**: Database access abstraction  

---

## Project Structure
```
    ClustredData/
├── docker-compose.yml # Docker compose setup (app + DB)
├── Dockerfile # Multi-stage Docker build
├── Makefile # Commands to build, run, test, and clean
├── pom.xml # Maven dependencies and build config
├── README.md # This documentation
├── src/
│ ├── main/
│ │ ├── java/com/progressoft/clusterddata/
│ │ │ ├── config/ # Spring and app configurations
│ │ │ ├── controller/ # REST API controllers
│ │ │ ├── validator/ # Validation logic (CurrencyValidator, etc.)
│ │ │ ├── dto/ # Data Transfer Objects
│ │ │ ├── entity/ # JPA entities (Deal, etc.)
│ │ │ ├── exception/ # Custom exceptions and global handlers
│ │ │ ├── mapper/ # MapStruct mappers (DealMapper)
│ │ │ ├── repository/ # Spring Data JPA repositories
│ │ │ ├── service/ # Business logic services (DealService)
│ │ │ └── infrastructure/ # Currency holders, CSV readers, etc.
│ └── resources/ # Application properties and CSV files
└── test/ # Unit and integration tests
└── target/ # Maven build outputs
````

---

## ⚙️ Installation & Setup

###  Prerequisites

- [Docker](https://www.docker.com/) & Docker Compose
- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)
---
###  Clone the Repository

```bash
git clone https://github.com/Douaa1819/ClusteredData--Warehouse.git
`````
---

### 📡 API - Endpoints

####  Create a Deal

- **Method:** `POST`
- **URL:** `http://localhost:8081/api/v1/deals`

####  Request Example

```json
{
  "id": "DEAL123456",
  "fromCurrencyCode": "USD",
  "toCurrency": "EUR",
  "dealAmount": 1500.75
}
```
 #### Réponse (HTTP 201 Created)
```json
{
"id": "DEAL123456",
"fromCurrencyCode": "USD",
"toCurrency": "EUR",
"dealTimestamp": "2025-07-28T15:01:23.456789",
"dealAmount": 1500.75
}
```
---
### Validation Rules
- id: must not be blank

- fromCurrencyCode and toCurrency:

- Must be different

- Must exist in the currency list (loaded from a CSV via CurrencyHolder)

- dealAmount: must be positive and not null

- Duplicate id will result in an error via RequestAlreadyExistException


---
### Error Handling
#### Custom exceptions:

- InvalidCurrencyException

- CurrencyNotAvailableException

- RequestAlreadyExistException

- Global error handling with @RestControllerAdvice:

- Returns a structured JSON with:

- Timestamp

- Error details

- Logging is handled with SLF4J

---

 ### Testing
Unit tests written with JUnit 5 and Mockito

-Test coverage includes:

-Deal service logic (DealService)

-Currency validation

-Repository interaction (mocked)

#### Run tests:
```
make test
```
---
### Dockerization
- Multi-stage Dockerfile for lightweight image builds

- Docker Compose to orchestrate the application and PostgreSQL

Volumes:

- PostgreSQL data persistence

- Maven cache to speed up builds

---


## 🛠 Makefile Commands

| Command       | Description                                 |
|---------------|---------------------------------------------|
| `make help`   | Display help message                        |
| `make up`     | Start application and database containers   |
| `make down`   | Stop and remove containers                  |
| `make test`   | Run unit tests                              |
| `make clean`  | Remove build artifacts (e.g. `target/`)     |
