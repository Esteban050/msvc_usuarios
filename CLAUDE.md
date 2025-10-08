# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot microservice for user management (`msvc-usuarios`), part of a larger microservices architecture. It uses:
- Spring Boot 3.5.6 with Java 17
- Spring Data JPA with PostgreSQL
- Spring Cloud OpenFeign for inter-service communication
- Lombok for reducing boilerplate
- Maven for build management

**Port**: 8001

## Build and Run Commands

### Build the project
```bash
./mvnw clean install
```

### Run the application
```bash
./mvnw spring-boot:run
```

### Run tests
```bash
./mvnw test
```

### Run a single test class
```bash
./mvnw test -Dtest=MsvcUsuariosApplicationTests
```

### Package without tests
```bash
./mvnw clean package -DskipTests
```

## Architecture

### Layered Architecture Pattern
The codebase follows a standard layered architecture:

```
controllers/     → REST endpoints (e.g., UserController)
services/        → Business logic (interface + implementation pattern)
repositories/    → Data access layer (Spring Data JPA)
models/entity/   → JPA entities
```

### Key Conventions

1. **Service Layer Pattern**: Services are defined as interfaces with separate implementation classes
   - Interface: `UserService`
   - Implementation: `UserServiceImpl`
   - Use `@Transactional` annotations in service implementations

2. **Dependency Injection**: Use constructor injection via Lombok's `@RequiredArgsConstructor`
   ```java
   @Service
   @RequiredArgsConstructor
   public class UserServiceImpl {
       private final UsuarioRepository repository;
   }
   ```

3. **Entity Classes**: Use selective Lombok annotations on `@Entity` classes
   - ✅ Use: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`
   - ❌ Avoid: `@Data`, `@ToString` (can cause JPA/lazy loading issues)

4. **Repository Layer**: Extend Spring Data's `CrudRepository` or `JpaRepository`

5. **Base Package**: All code under `org.esteban.springboot.springmvc.app.msvcusuarios`

## Database Configuration

- Database: PostgreSQL
- Configuration location: `src/main/resources/application.properties`
- Entity table mapping uses `@Table(name = "...")` annotations
- Primary keys use `@GeneratedValue(strategy = GenerationType.IDENTITY)`

## Microservices Context

This service is part of a microservices ecosystem:
- Spring Cloud OpenFeign is configured for service-to-service communication
- Designed to work with other microservices in the system
- RESTful API exposed at `/usuarios` base path
