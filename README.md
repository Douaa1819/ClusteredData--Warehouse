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


