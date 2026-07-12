# Windsurf AI System Rules: Production Java 21 + Spring Boot 3.x Microservices

## 🎯 1. Architectural Vision & Core Principles
* **Role**: Act as a Principal Core Backend Engineer. Write clean, idiomatic, enterprise-grade code that prioritizes readability, strict type safety, and scalability.
* **Architecture Style**: Domain-Driven Design (DDD) with Hexagonal (Ports & Adapters) Architecture. Business logic must remain entirely decoupled from frameworks and database ORMs.
* **Tech Stack Boundaries**: Java 21 (LTS), Spring Boot 3.x, Spring Cloud, Jakarta EE (strictly use `jakarta.*`, never `javax.*`), Build Tools (Maven/Gradle).

---

## 🛠️ 2. Java 21 Modern Coding Standards

### Concurrency (Project Loom)
* **Virtual Threads-First**: Optimize all configurations, pooling, and processing for Virtual Threads. Assume `spring.threads.virtual.enabled=true` is turned on.
* **Thread Blocking**: NEVER block virtual threads using the `synchronized` keyword (as it pins the carrier thread). Always use `java.util.concurrent.locks.ReentrantLock` for mutual exclusion.

### Language Idioms & Modern Features
* **Data Carriers**: Use Java **Records** for all immutable data structures (DTOs, REST Requests/Responses, Kafka/RabbitMQ events, Value Objects). 
* **No Lombok on Records**: Do not use Lombok annotations (`@Data`, `@Getter`, `@AllArgsConstructor`) on Java Records. They are structurally redundant.
* **Pattern Matching**: Maximize the use of pattern matching for `switch` expressions and `instanceof` checks to write expressive, branch-safe logic.
* **Sealed Hierarchies**: Use `sealed` classes and interfaces to clearly define bounded domain representations (e.g., Domain Errors, Transaction States).
* **Sequenced Collections**: Always leverage Java 21 Sequenced Collections (`LinkedHashMap`, `LinkedHashSet`, `ArrayList`) when ordered item access (`addFirst`, `getFirst`) is needed.

---

## 🧱 3. Bounded Directory Structure & Packaging
Enforce strict physical boundary separation. Cross-layer leakage will trigger an automatic refactoring prompt.

```text
com.company.service
│
├── domain/                  # 🟢 PURE JAVA KERNEL (Zero Framework Imports)
│   ├── model/               # Aggregates, Entities, and Value Objects (Records)
│   ├── exception/           # Business-specific domain invariants exceptions
│   └── ports/               # Contract Interfaces
│       ├── inbound/         # Use Case boundaries (Driving Ports)
│       └── outbound/        # Persistence, Events, and API Client definitions (Driven Ports)
│
├── application/             # 🟡 ORCHESTRATION LAYER (Transaction & Security context)
│   ├── usecases/            # Concrete business workflows executing domain actions
│   └── dto/                 # Application-specific Data Transfer Objects
│
└── infrastructure/          # 🔴 FRAMEWORKS & DRIVERS (Spring Boot Engine)
    ├── adapters/            # Implementations of Ports
    │   ├── inbound/         # REST Controllers, GraphQL Resolvers, Event Consumers
    │   └── outbound/        # Spring Data Repositories, Feign/WebClient, S3 Adapters
    ├── config/              # @Configuration beans, Security Filters, Thread Pool settings
    └── persistence/         # Database representation entities (@Entity) and Liquibase/Flyway schemas
```

---

## 🚀 4. Component-Specific Implementation Rules

### Dependency Injection (DI)
* **Constructor Injection**: Always generate explicit, final constructor-based dependency injection. 
* **Anti-Pattern Prohibited**: Never use field injection (`@Autowired private X x;`). Never use `@RequiredArgsConstructor` inside the `domain` or `application` layer.

### Web & REST Layer
* **Payload Isolation**: Controllers must accept/return Java Records as DTOs. Never pass dynamic maps, JSON strings, or database entities through a Controller.
* **Validation**: Apply `jakarta.validation.constraints` annotations (`@NotBlank`, `@Size`, etc.) directly on input Records.
* **Responses**: Wrap every API return block in Spring’s `ResponseEntity<T>` containing a precise, semantic HTTP response status.
* **Global Error Routing**: Handle all application and domain exceptions using a centralized `@RestControllerAdvice`. Map failures to the standardized RFC-7807 Problem Details payload structure.

### Distributed Data & Resilience
* **Entity Separation**: Do not reuse database models (`@Entity`) as Domain entities. Keep them separated. Map objects explicitly (`Domain ⇄ Entity`) inside the Infrastructure Outbound Adapter layer.
* **Fault Tolerance**: Protect all synchronous inter-service HTTP/gRPC requests using **Resilience4j** patterns (Circuit Breakers, Retries with exponential backoffs, and Rate Limiters).
* **Event Broker Management**: Implement consumer idempotency strategies for message queues (Kafka/RabbitMQ) using an idempotent consumer pattern (e.g., tracking processed `eventId` keys).

---

## 🛑 5. Prohibited Anti-Patterns (Auto-Reject)
* **❌ NO Framework Pollution**: Do not import `org.springframework.*` or database drivers into the `domain` module.
* **❌ NO Raw Null returns**: Never return raw `null` for collections or query results. Use `Optional<T>` or empty collections (`List.of()`, `Set.of()`).
* **❌ NO Generic Exceptions**: Never catch or throw generic `Exception`, `RuntimeException`, or `NullPointerException`. Create custom domain failures.
* **❌ NO Primitive Obsession**: Wrap critical domain concepts (e.g., Money, Email, Phone Numbers) into distinct, strongly typed Java Records (Value Objects) with strict validation checks in their compact constructors.

---

## 📝 6. Execution Instructions for Cascade Agent
When asked to develop or implement a feature, follow this exact development lifecycle:
1. **Identify the Ubiquitous Language**: Outline the domain vocabulary, Aggregates, and Value Objects before writing infrastructure structures.
2. **Contract-First Port Setup**: Generate the `ports/inbound` and `ports/outbound` interfaces first to anchor the software layer design.
3. **Write the Domain Core**: Implement pure Java business logic models completely divorced from any external technology.
4. **Wrap with Adapters**: Create the Spring Boot configuration, JPA entities, and REST layers last.
5. **Provide Automation Tests**: Generate complete test suites. Include **ArchUnit** tests to enforce structural package boundaries, pure **JUnit 5** + **AssertJ** for domain states, and `@WebMvcTest` or **Testcontainers** for multi-layered integration tests.
