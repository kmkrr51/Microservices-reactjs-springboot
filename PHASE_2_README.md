# Phase 2: Core Microservices Implementation Guide

**Status**: 🚀 IN PROGRESS  
**Sprint**: Sprint 2-1 (CMDB Foundation & ITSM Setup)  
**Date**: July 12, 2026

---

## Quick Start

### Prerequisites
- Java 21 JDK
- Maven 3.9+
- PostgreSQL 14+
- MongoDB 5.0+
- Apache Kafka 3.x
- Docker & Docker Compose (optional)

### Build Phase 2 Services

```bash
# Build all Phase 2 services
mvn clean install -pl cmdb,itsm,itom,itam

# Build specific service
mvn clean install -pl itsm

# Build without tests
mvn clean install -pl cmdb,itsm,itom,itam -DskipTests
```

### Run Services

```bash
# CMDB Service (Port 8000)
java -jar cmdb/target/cmdb-1.0.0-SNAPSHOT.jar

# ITSM Service (Port 8001)
java -jar itsm/target/itsm-1.0.0-SNAPSHOT.jar

# ITOM Service (Port 8002)
java -jar itom/target/itom-1.0.0-SNAPSHOT.jar

# ITAM Service (Port 8003)
java -jar itam/target/itam-1.0.0-SNAPSHOT.jar
```

### Run Tests

```bash
# Run all tests
mvn test

# Run tests for specific service
mvn test -pl itsm

# Run with coverage
mvn clean test jacoco:report
```

---

## Service Ports

| Service | Port | Database | Purpose |
|---------|------|----------|---------|
| CMDB | 8000 | PostgreSQL + MongoDB | Configuration Management Database |
| ITSM | 8001 | PostgreSQL | IT Service Management |
| ITOM | 8002 | PostgreSQL + MongoDB | IT Operations Management |
| ITAM | 8003 | PostgreSQL | IT Asset Management |

---

## API Endpoints

### ITSM Service (Port 8001)

#### Create Incident
```bash
POST /api/v1/incidents
Content-Type: application/json

{
  "incidentNumber": "INC-001",
  "title": "System Down",
  "description": "Critical system outage",
  "priority": "CRITICAL",
  "reporter": "user@example.com"
}
```

#### Get Incident
```bash
GET /api/v1/incidents/{id}
```

#### Get All Incidents
```bash
GET /api/v1/incidents
```

#### Get Incident by Number
```bash
GET /api/v1/incidents/number/{incidentNumber}
```

#### Update Incident Status
```bash
PUT /api/v1/incidents/{id}/status?status=IN_PROGRESS
```

#### Assign Incident
```bash
PUT /api/v1/incidents/{id}/assign?assignee=support@example.com
```

#### Delete Incident
```bash
DELETE /api/v1/incidents/{id}
```

---

## Database Setup

### PostgreSQL

```sql
-- Create databases
CREATE DATABASE cmdb_db;
CREATE DATABASE itsm_db;
CREATE DATABASE itom_db;
CREATE DATABASE itam_db;

-- Grant permissions
GRANT ALL PRIVILEGES ON DATABASE cmdb_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE itsm_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE itom_db TO postgres;
GRANT ALL PRIVILEGES ON DATABASE itam_db TO postgres;
```

### MongoDB

```bash
# Create databases (automatic on first write)
use cmdb_db
use itom_db
```

---

## Kafka Setup

### Create Topics

```bash
# CMDB Topics
kafka-topics.sh --create --topic cmdb.events --bootstrap-servers localhost:9092 --partitions 3 --replication-factor 1

# ITSM Topics
kafka-topics.sh --create --topic itsm.incidents --bootstrap-servers localhost:9092 --partitions 3 --replication-factor 1

# ITOM Topics
kafka-topics.sh --create --topic itom.assets --bootstrap-servers localhost:9092 --partitions 3 --replication-factor 1

# ITAM Topics
kafka-topics.sh --create --topic itam.hardware --bootstrap-servers localhost:9092 --partitions 3 --replication-factor 1
```

### Monitor Topics

```bash
# List topics
kafka-topics.sh --list --bootstrap-servers localhost:9092

# Monitor topic messages
kafka-console-consumer.sh --topic itsm.incidents --bootstrap-servers localhost:9092 --from-beginning
```

---

## Docker Compose

### Start All Services

```bash
docker-compose up -d
```

### Stop All Services

```bash
docker-compose down
```

### View Logs

```bash
docker-compose logs -f itsm
docker-compose logs -f cmdb
docker-compose logs -f itom
docker-compose logs -f itam
```

---

## OpenAPI Documentation

Access Swagger UI for each service:

- **CMDB**: http://localhost:8000/swagger-ui.html
- **ITSM**: http://localhost:8001/swagger-ui.html
- **ITOM**: http://localhost:8002/swagger-ui.html
- **ITAM**: http://localhost:8003/swagger-ui.html

---

## Health Checks

```bash
# CMDB Health
curl http://localhost:8000/actuator/health

# ITSM Health
curl http://localhost:8001/actuator/health

# ITOM Health
curl http://localhost:8002/actuator/health

# ITAM Health
curl http://localhost:8003/actuator/health
```

---

## Metrics

Access Prometheus metrics:

```bash
# ITSM Metrics
curl http://localhost:8001/actuator/metrics

# Prometheus endpoint
curl http://localhost:8001/actuator/prometheus
```

---

## Testing

### Unit Tests

```bash
# Run all unit tests
mvn test

# Run specific test class
mvn test -Dtest=IncidentTest

# Run with coverage report
mvn clean test jacoco:report
```

### Integration Tests

```bash
# Run integration tests
mvn verify

# Run specific integration test
mvn verify -Dit.test=IncidentControllerIT
```

### Test Coverage

```bash
# Generate coverage report
mvn clean test jacoco:report

# View report
open target/site/jacoco/index.html
```

---

## Code Quality

### SonarQube Analysis

```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=snowrepo-phase2 \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=your-token
```

### Code Style

All services follow:
- 2-space indentation
- < 100 character line length
- camelCase naming convention
- Complete Javadoc
- No code duplication

---

## Troubleshooting

### Database Connection Issues

```bash
# Test PostgreSQL connection
psql -h localhost -U postgres -d itsm_db

# Test MongoDB connection
mongo mongodb://localhost:27017/itom_db
```

### Kafka Connection Issues

```bash
# Test Kafka broker
kafka-broker-api-versions.sh --bootstrap-servers localhost:9092
```

### Port Already in Use

```bash
# Find process using port
lsof -i :8001

# Kill process
kill -9 <PID>
```

---

## Development Workflow

### 1. Create Feature Branch
```bash
git checkout -b feature/itsm-problem-management
```

### 2. Make Changes
```bash
# Edit code, add tests
```

### 3. Run Tests
```bash
mvn clean test -pl itsm
```

### 4. Commit Changes
```bash
git add .
git commit -m "[itsm] Add problem management domain model"
```

### 5. Push and Create PR
```bash
git push origin feature/itsm-problem-management
```

---

## Next Steps

### Sprint 2-2 (Next 2 Weeks)
1. Implement additional domain models:
   - Problem, Change, ServiceRequest (ITSM)
   - ServiceMap, MonitoringEvent (ITOM)
   - SoftwareAsset, License, Contract (ITAM)

2. Complete unit and integration tests (85%+ coverage)

3. Implement event consumers for inter-service communication

4. Create comprehensive API documentation

### Sprint 2-3 (Weeks 5-6)
1. Complete application layer for all services

2. Implement error handling and validation

3. Performance testing and optimization

4. Integration testing across services

---

## Resources

- [Spring Boot 3.1 Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [MongoDB Documentation](https://docs.mongodb.com/)

---

## Support

For questions or issues:
1. Check the [PHASE_2_IMPLEMENTATION.md](PHASE_2_IMPLEMENTATION.md) for detailed information
2. Review existing code examples in the services
3. Check test cases for usage patterns
4. Contact the development team

---

**Last Updated**: July 12, 2026  
**Status**: 🚀 IN PROGRESS
