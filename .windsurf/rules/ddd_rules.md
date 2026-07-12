# Windsurf AI System Rules: Java 21 + Spring Boot 3.x DDD Microservices

## 🎯 High-Level Principles
* **Vibe Engineering Goal**: Act as a highly autonomous staff software engineer. Focus on strict architectural enforcement so you ("Cascade") can safely generate full features without introducing architectural drift.
* **Paradigm**: Domain-Driven Design (DDD) with a Hexagonal/Clean Architecture flavor. Core domain logic must remain pure and free from framework dependencies.
* **Tech Stack Constraints**: Java 21, Spring Boot 3.x, Spring Cloud, Maven/Gradle, Jakarta EE (never use `javax.*`).

---

## 🛠️ Java 21 & Spring Boot 3.x Modern Standards

### 1. Concurrency & Performance
* ALWAYS optimize for **Virtual Threads (Project Loom)**. 
* Add `spring.threads.virtual.enabled=true` to configurations automatically.
* NEVER block virtual threads with `synchronized` blocks. Use `ReentrantLock` instead.

### 2. Language Features
* **Data Carriers**: Use Java **Records** for DTOs, API Requests/Responses, and Domain Events. Records must be immutable.
* **Pattern Matching**: Utilize enhanced switch statements and pattern matching for `instanceof` to keep logic readable.
* **Sequenced Collections**: Use `LinkedHashSet` or explicit sequenced collection APIs when ordering matters.

---

## 🧱 DDD Structural Rules & Package Layout
Every microservice must follow this strict module/package architecture. Never let your code generation leak cross-layer concerns.

```text
com.company.service
│
├── domain/                  # 🟢 PURE JAVA  
│   ├── model/               # Aggregates, Entities, Value Objects
│   ├── exception/           # Domain-specific business exceptions
│   ├── events/              # Immutable Domain Events
│   └── ports/               # Interfaces defining driving/driven workflows
│       ├── inbound/         # Use-case interfaces (Inbound Ports)
│       └── outbound/        # Repository and External Client interfaces (Outbound Ports)
│
├── application/             # 🟡 Orchestration Layer
│   ├── usecases/            # Implementation of inbound ports
│   └── services/            # Application services orchestration (Transactions)
│
└── infrastructure/          # 🔴 Frameworks, Drivers, Tools
    ├── adapters/            # Implementations of Ports
    │   ├── inbound/         # REST Controllers, Kafka/Rabbit Consumers, GraphQL
    │   └── outbound/        # JPA Repositories, Feign Clients, Cloud Services
    ├── config/              # Spring Boot @Configuration, Bean definitions
    └── persistence/         # JPA/NoSQL Entities, Database Mappings
```

---

## 🚀 Layer-Specific Rules for Code Generation

### 1. Domain Layer (The Core)
* **Rule**: Zero framework imports. No `@Service`, `@Autowired`, `@Entity`, or `@Table`.
* **Aggregates & Entities**: Must protect their invariants. Use private setters. Expose domain methods like `order.fulfill()` instead of generic setters.
* **Value Objects**: Must be implemented using Java **Records** with custom validation inside the record constructor.
* **Validation**: Perform domain-level structural validation manually or via pure Java factories; do not rely on `jakarta.validation` here.

### 2. Application Layer
* **Rule**: Orchestrates transactions and maps infrastructure inputs to the domain.
* **Transaction Management**: Mark use-case execution entry points with `@Transactional(readOnly = ...)` at this layer.
* **Mappers**: Map incoming DTOs to Domain objects before hitting the Domain layer.

### 3. Infrastructure Layer (Adapters)
* **REST Controllers**:
  * Return `ResponseEntity<T>` with accurate HTTP statuses.
  * Use Java records for requests/responses.
  * Apply `jakarta.validation.constraints` (`@Valid`, `@NotNull`) directly onto the request Records.
  * Controllers must contain **zero** business logic. They call Inbound Ports.
* **Persistence (Spring Data JPA / Mongo)**:
  * Maintain separate database entity classes (e.g., `OrderJpaEntity`) separate from Domain Entities (`Order`).
  * Implement the Domain's Outbound Port interface inside the adapter, mapping `Domain Entity ⇄ JPA Entity` via a clean mapper class.

---

## 🛑 Prohibited Anti-Patterns (Enforced via Auto-Reject)
* **❌ NO Leakage**: Do not use database entities (`@Entity`) inside the Domain layer.
* **❌ NO Lombok in Domain**: Use native Java 21 Records instead of Lombok `@Data` or `@Value` for domain types where possible.
* **❌ NO Field Injection**: Always use constructor-based dependency injection. Do not generate `@Autowired private Service s;`.
* **❌ NO Generic Exceptions**: Never throw generic `RuntimeException`. Always generate specific domain exceptions handled by an infrastructure-level `@ControllerAdvice`.

---

## 📝 Generation Guidelines for Cascade Agent
When I ask you to "implement feature X":
1. **Think step-by-step**: Identify the Ubiquitous Language terms, Bounded Context boundaries, Aggregates, and Value Objects first.
2. **Draft the Domain Ports** before writing any implementation code.
3. Generate the pure Java Domain Model, then the Application Use Cases, and finally wrap them in Spring Boot Infrastructure Adapters.
4. Always provide unit tests matching this paradigm: **Architecture tests** (ArchUnit) to ensure rules aren't broken, **Domain tests** (pure JUnit 5), and **Integration tests** (`@WebMvcTest` / `Testcontainers`).
