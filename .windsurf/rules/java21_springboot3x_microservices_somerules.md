# Windsurf AI System Rules: Java 21 + Spring Boot 3.x Microservices Backend

## 🎯 Architectural Vision & Core Principles
* **Role**: Act as an expert Principal Backend Engineer. Write clean, idiomatic, enterprise-grade code.
* **Architecture**: Cloud-native microservices using Domain-Driven Design (DDD) guidelines. Separate business logic from infrastructure frameworks.
* **Tech Stack**: Java 21 LTS, Spring Boot 3.x, Spring Cloud, Maven/Gradle, Jakarta EE (never use `javax.*`).

---

## 🛠️ Java 21 Modern Coding Standards

### 1. Loom & Virtual Threads
* **Rule**: Optimize configurations for **Virtual Threads**. Assume `spring.threads.virtual.enabled=true` is active.
* **Prohibition**: Never block virtual threads using the `synchronized` keyword. Always generate `ReentrantLock` for resource locking.

### 2. Modern Language Features
* **Data Holders**: Use Java **Records** for all immutable data carriers (DTOs, API Request/Response bodies, Domain Events, Kafka payloads).
* **Pattern Matching**: Utilize pattern matching for `switch` expressions and `instanceof` to eliminate verbose type-casting.
* **Sealed Types**: Use `sealed` classes/interfaces to explicitly define closed hierarchies (e.g., specific Domain Error or Payment Method hierarchies).
* **Collections**: Prefer Sequenced Collections (`LinkedHashMap`, `ArrayList`) when elements need a predictable, defined order.

---

## 🧱 Microservice Directory & Structural Rules
Enforce clean boundary separation across every service. No data leakage or cross-layer bleeding allowed.

```text
com.company.service
│
├── domain/                  # 🟢 PURE JAVA KERNEL (Zero Framework Dependencies)
│   ├── model/               # Aggregates, Entities, Value Objects (Records)
│   ├── exception/           # Business-specific domain exceptions
│   └── ports/               # Driving (Inbound Use Cases) & Driven (Outbound Repos/Clients)
│
├── application/             # 🟡 ORCHESTRATION LAYER (Transaction & Auth Contexts)
│   ├── usecases/            # Core business workflows implementing inbound ports
│   └── dto/                 # Application/API Data Transfer Objects
│
└── infrastructure/          # 🔴 RUNTIME INFRASTRUCTURE (Spring Boot, Database, Clients)
    ├── adapters/            # Inbound (REST/GraphQL/Kafka) & Outbound (JPA/Feign/S3) Adapters
    ├── config/              # @Configuration beans, Security, Virtual Thread configs
    └── persistence/         # Database Entities (@Entity), Liquibase/Flyway migrations
```

---

## 🚀 Component-Specific Implementation Rules

### 1. Dependency Injection & Configuration
* **Rule**: Use explicit **Constructor Injection**. Never generate field-level injection (`@Autowired`).
* **Rule**: Declare all Spring beans programmatically inside `infrastructure/config/` instead of polluting the `domain/` layer with annotations.

### 2. REST API & Web Layer
* **Payloads**: Use validation tags (`@NotNull`, `@Size`, etc.) from `jakarta.validation.constraints` directly on Request Records.
* **Responses**: Always wrap controller responses in Spring's `ResponseEntity<T>` with explicit, accurate HTTP status codes.
* **Error Handling**: Consolidate exception routing using a global `@RestControllerAdvice` mapping specific domain exceptions to RFC-7807 Problem Details payloads.

### 3. Resilience & Distributed Data
* **Data Access**: Maintain structural isolation between database types (`@Entity`) and pure `Domain Models`. Map between them inside the repository adapter.
* **Fault Tolerance**: Integrate Resilience4j patterns (Circuit Breakers, Retries, Rate Limiters) whenever calling external microservices via HTTP/gRPC.
* **Messaging**: Implement idempotent message consumers for Kafka/RabbitMQ events to safely handle network duplicate retries.

---

## 🛑 Prohibited Anti-Patterns (Auto-Reject)
* **❌ NO Lombok in Records**: Do not generate Lombok annotations (`@Data`, `@Getter`) on Java Records. They are natively complete.
* **❌ NO Leakage**: Never expose database persistence models (`@Entity`) to the API layer or the core Domain layer.
* **❌ NO Null Returns**: Avoid returning raw `null` for list structures or missing values. Return empty collections or `Optional<T>`.
* **❌ NO Generic Exceptions**: Never throw raw `RuntimeException` or `Exception`. Generate descriptive, domain-specific exception types instead.

---

## 📝 Generation Guidelines for Cascade Agent
When I ask you to build a component, feature, or service:
1. **Analyze Dependencies**: Confirm that core domain logic has zero imports from `org.springframework.*`.
2. **Apply Java 21 Idioms**: Default to Records, Switch Expressions, and Scoped-Values/Virtual Threads where applicable.
3. **Build defensively**: Generate comprehensive structural and component unit tests using **JUnit 5**, **Mockito**, and **AssertJ**.
