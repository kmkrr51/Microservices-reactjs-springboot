# Code Setup & Development Environment Guide
## ServiceNow Microservices - Java 21, Spring Boot 3.x

**Document Version**: 1.0  
**Date**: July 12, 2026  
**Status**: Ready for Implementation  
**Purpose**: Setup development environment and initialize code structure

---

## Table of Contents

1. [System Requirements](#system-requirements)
2. [Development Environment Setup](#development-environment-setup)
3. [Git Repository Setup](#git-repository-setup)
4. [Maven Project Structure](#maven-project-structure)
5. [IDE Configuration](#ide-configuration)
6. [Docker Setup](#docker-setup)
7. [Code Quality Tools](#code-quality-tools)
8. [Development Workflow](#development-workflow)
9. [Troubleshooting](#troubleshooting)

---

## System Requirements

### Hardware Requirements
- **CPU**: Intel/AMD processor with 4+ cores
- **RAM**: 16GB minimum (32GB recommended)
- **Disk Space**: 50GB free space (for Docker images, databases, etc.)
- **Network**: Stable internet connection

### Software Requirements

**Java Development Kit (JDK)**:
- Java 21 LTS (OpenJDK or Oracle JDK)
- Download: https://www.oracle.com/java/technologies/downloads/#java21
- Verify: `java -version` (should show Java 21)

**Maven**:
- Apache Maven 3.9+
- Download: https://maven.apache.org/download.cgi
- Verify: `mvn -version` (should show Maven 3.9+)

**Git**:
- Git 2.40+
- Download: https://git-scm.com/downloads
- Verify: `git --version`

**Docker**:
- Docker Desktop 24.0+
- Download: https://www.docker.com/products/docker-desktop
- Verify: `docker --version` and `docker-compose --version`

**IDE** (Choose one):
- IntelliJ IDEA Community/Ultimate (2023.3+)
- Eclipse IDE (2023.09+)
- VS Code with Java extensions

**Additional Tools**:
- Git GUI client (optional): GitHub Desktop, GitKraken, SourceTree
- API testing: Postman, Insomnia
- Database client: DBeaver, pgAdmin, MongoDB Compass

---

## Development Environment Setup

### Step 1: Install Java 21

**macOS (using Homebrew)**:
```bash
brew install openjdk@21
# Set JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc
source ~/.zshrc
```

**Linux (Ubuntu/Debian)**:
```bash
sudo apt update
sudo apt install openjdk-21-jdk
# Verify
java -version
```

**Windows**:
- Download from Oracle JDK website
- Run installer
- Set JAVA_HOME environment variable
- Verify: `java -version`

### Step 2: Install Maven

**macOS**:
```bash
brew install maven
# Verify
mvn -version
```

**Linux**:
```bash
sudo apt install maven
# Verify
mvn -version
```

**Windows**:
- Download from Maven website
- Extract to C:\Program Files\maven
- Add to PATH environment variable
- Verify: `mvn -version`

### Step 3: Install Git

**macOS**:
```bash
brew install git
# Verify
git --version
```

**Linux**:
```bash
sudo apt install git
# Verify
git --version
```

**Windows**:
- Download from git-scm.com
- Run installer
- Verify: `git --version`

### Step 4: Install Docker

**macOS & Windows**:
- Download Docker Desktop from docker.com
- Run installer
- Start Docker Desktop
- Verify: `docker --version` and `docker-compose --version`

**Linux**:
```bash
sudo apt update
sudo apt install docker.io docker-compose
sudo usermod -aG docker $USER
# Verify
docker --version
docker-compose --version
```

### Step 5: Configure Git

```bash
# Set global configuration
git config --global user.name "Your Name"
git config --global user.email "your.email@company.com"

# Configure line endings (important for cross-platform development)
git config --global core.autocrlf true  # Windows
git config --global core.autocrlf input # macOS/Linux

# Setup SSH key (optional but recommended)
ssh-keygen -t ed25519 -C "your.email@company.com"
# Add public key to GitHub/GitLab
```

---

## Git Repository Setup

### Step 1: Clone Repository

```bash
# Clone the repository
git clone https://github.com/your-org/snowrepo-springboot.git
cd snowrepo-springboot

# Verify branches
git branch -a
```

### Step 2: Setup Local Branches

```bash
# Create local develop branch
git checkout -b develop origin/develop

# Verify you're on develop
git status
```

### Step 3: Create Feature Branch

```bash
# Create feature branch for your work
git checkout -b feature/your-feature-name

# Example: feature/setup-shared-libraries
git checkout -b feature/setup-shared-libraries
```

### Step 4: Git Workflow

**Daily workflow**:
```bash
# Start of day: pull latest changes
git pull origin develop

# During day: commit your work
git add .
git commit -m "[scope] message"  # Follow commit format

# End of day: push your work
git push origin feature/your-feature-name

# Create pull request on GitHub/GitLab
```

**Commit format** (from user rules):
```
[scope] message

Examples:
[shared-domain] Add AggregateRoot base class
[api-gateway] Configure OAuth 2.0 authentication
[database] Create PostgreSQL migration script
```

---

## Maven Project Structure

### Step 1: Understand Project Structure

```
snowrepo-springboot/
├── pom.xml                          # Parent POM
├── shared-domain/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/com/snowrepo/domain/
│   │   │   ├── AggregateRoot.java
│   │   │   ├── ValueObject.java
│   │   │   ├── DomainEvent.java
│   │   │   └── Entity.java
│   │   └── test/java/com/snowrepo/domain/
│   └── target/
├── shared-exceptions/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/com/snowrepo/exception/
│   │   └── test/java/com/snowrepo/exception/
│   └── target/
├── shared-utils/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/com/snowrepo/util/
│   │   └── test/java/com/snowrepo/util/
│   └── target/
├── shared-logging/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/com/snowrepo/logging/
│   │   └── test/java/com/snowrepo/logging/
│   └── target/
├── api-gateway/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/snowrepo/gateway/
│   │   │   └── resources/application.yml
│   │   └── test/
│   └── target/
├── docs/
│   ├── architecture/
│   ├── api/
│   └── guides/
└── docker-compose.yml
```

### Step 2: Build Project

```bash
# Build entire project
mvn clean install

# Build specific module
mvn clean install -pl shared-domain

# Skip tests (faster build)
mvn clean install -DskipTests

# Build with specific profile
mvn clean install -P dev
```

### Step 3: Run Tests

```bash
# Run all tests
mvn test

# Run tests for specific module
mvn test -pl shared-domain

# Run specific test class
mvn test -Dtest=AggregateRootTest

# Run with coverage
mvn clean test jacoco:report
```

### Step 4: Code Quality Analysis

```bash
# Run SonarQube analysis
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=snowrepo-springboot \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=your-token

# Check code coverage
mvn jacoco:report
# Open target/site/jacoco/index.html
```

---

## IDE Configuration

### IntelliJ IDEA Setup

**Step 1: Import Project**:
1. Open IntelliJ IDEA
2. File → Open → Select project root directory
3. Select "Open as Project"
4. Wait for indexing to complete

**Step 2: Configure JDK**:
1. File → Project Structure → Project
2. Set Project SDK to Java 21
3. Set Project language level to 21

**Step 3: Configure Maven**:
1. File → Settings → Build, Execution, Deployment → Build Tools → Maven
2. Set Maven home path
3. Set User settings file
4. Set Local repository

**Step 4: Code Style**:
1. File → Settings → Editor → Code Style
2. Import code style: `docs/code-style.xml`
3. Set line length to 100 (from user rules)
4. Set indent to 2 spaces (from user rules)

**Step 5: Install Plugins**:
- Lombok
- SonarLint
- Docker
- Kubernetes
- Spring Boot Assistant

### Eclipse IDE Setup

**Step 1: Import Project**:
1. Open Eclipse
2. File → Import → Maven → Existing Maven Projects
3. Select project root directory
4. Click Finish

**Step 2: Configure JDK**:
1. Window → Preferences → Java → Installed JREs
2. Add JDK 21
3. Set as default

**Step 3: Code Style**:
1. Window → Preferences → Java → Code Style → Formatter
2. Import formatter: `docs/eclipse-formatter.xml`

### VS Code Setup

**Step 1: Install Extensions**:
- Extension Pack for Java
- Spring Boot Extension Pack
- Docker
- Kubernetes
- REST Client

**Step 2: Configure Settings**:
Create `.vscode/settings.json`:
```json
{
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.format.settings.url": "docs/eclipse-formatter.xml",
  "editor.formatOnSave": true,
  "editor.defaultFormatter": "redhat.java",
  "[java]": {
    "editor.defaultFormatter": "redhat.java",
    "editor.formatOnSave": true
  }
}
```

---

## Docker Setup

### Step 1: Create Docker Compose File

Create `docker-compose.yml` in project root:

```yaml
version: '3.8'

services:
  # PostgreSQL Database
  postgres:
    image: postgres:15-alpine
    container_name: snowrepo-postgres
    environment:
      POSTGRES_DB: snowrepo
      POSTGRES_USER: snowrepo
      POSTGRES_PASSWORD: snowrepo123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - snowrepo-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U snowrepo"]
      interval: 10s
      timeout: 5s
      retries: 5

  # MongoDB Database
  mongodb:
    image: mongo:6.0
    container_name: snowrepo-mongodb
    environment:
      MONGO_INITDB_ROOT_USERNAME: snowrepo
      MONGO_INITDB_ROOT_PASSWORD: snowrepo123
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db
    networks:
      - snowrepo-network
    healthcheck:
      test: ["CMD", "mongosh", "--eval", "db.adminCommand('ping')"]
      interval: 10s
      timeout: 5s
      retries: 5

  # Elasticsearch
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.0.0
    container_name: snowrepo-elasticsearch
    environment:
      - discovery.type=single-node
      - xpack.security.enabled=false
    ports:
      - "9200:9200"
    volumes:
      - elasticsearch_data:/usr/share/elasticsearch/data
    networks:
      - snowrepo-network
    healthcheck:
      test: ["CMD-SHELL", "curl -f http://localhost:9200/_cluster/health || exit 1"]
      interval: 10s
      timeout: 5s
      retries: 5

  # Kafka
  kafka:
    image: confluentinc/cp-kafka:7.4.0
    container_name: snowrepo-kafka
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    ports:
      - "9092:9092"
    networks:
      - snowrepo-network
    healthcheck:
      test: ["CMD", "kafka-broker-api-versions.sh", "--bootstrap-server", "localhost:9092"]
      interval: 10s
      timeout: 5s
      retries: 5

  # Zookeeper (required by Kafka)
  zookeeper:
    image: confluentinc/cp-zookeeper:7.4.0
    container_name: snowrepo-zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    ports:
      - "2181:2181"
    networks:
      - snowrepo-network

volumes:
  postgres_data:
  mongodb_data:
  elasticsearch_data:

networks:
  snowrepo-network:
    driver: bridge
```

### Step 2: Start Docker Services

```bash
# Start all services
docker-compose up -d

# Verify services are running
docker-compose ps

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Remove volumes (careful!)
docker-compose down -v
```

### Step 3: Verify Connectivity

```bash
# Test PostgreSQL
psql -h localhost -U snowrepo -d snowrepo

# Test MongoDB
mongosh --host localhost --username snowrepo --password snowrepo123

# Test Elasticsearch
curl http://localhost:9200/_cluster/health

# Test Kafka
kafka-console-producer.sh --broker-list localhost:9092 --topic test
```

---

## Code Quality Tools

### Step 1: SonarQube Setup

**Start SonarQube**:
```bash
# Using Docker
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest

# Access at http://localhost:9000
# Default credentials: admin/admin
```

**Configure SonarQube**:
1. Create quality gate
2. Create project
3. Generate token

**Run analysis**:
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=snowrepo-springboot \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=your-token
```

### Step 2: Code Coverage (JaCoCo)

**Generate coverage report**:
```bash
mvn clean test jacoco:report
```

**View report**:
```bash
# Open in browser
open target/site/jacoco/index.html  # macOS
xdg-open target/site/jacoco/index.html  # Linux
start target/site/jacoco/index.html  # Windows
```

### Step 3: Checkstyle

**Run checkstyle**:
```bash
mvn checkstyle:check
```

**Generate report**:
```bash
mvn checkstyle:checkstyle
# View at target/site/checkstyle.html
```

---

## Development Workflow

### Daily Development Workflow

**Morning**:
```bash
# 1. Pull latest changes
git pull origin develop

# 2. Create/switch to feature branch
git checkout feature/your-feature

# 3. Build project
mvn clean install

# 4. Run tests
mvn test
```

**During Day**:
```bash
# 1. Make code changes
# 2. Run tests frequently
mvn test

# 3. Commit regularly
git add .
git commit -m "[scope] message"

# 4. Push to remote
git push origin feature/your-feature
```

**End of Day**:
```bash
# 1. Run full test suite
mvn clean test

# 2. Run code quality checks
mvn clean verify sonar:sonar

# 3. Push final changes
git push origin feature/your-feature

# 4. Create pull request (if ready)
```

### Code Review Process

**Before Creating Pull Request**:
- [ ] Code compiles without errors
- [ ] All tests pass
- [ ] Code coverage > 85%
- [ ] SonarQube score > 80%
- [ ] Code follows style guidelines
- [ ] Documentation updated
- [ ] Commit messages follow format

**Pull Request Checklist**:
- [ ] Title follows format: `[scope] description`
- [ ] Description explains changes
- [ ] Links to related issues
- [ ] Screenshots/examples (if applicable)
- [ ] Requests 2+ reviewers

**Review Process**:
1. Create pull request
2. Wait for CI/CD pipeline to pass
3. Request code review from team members
4. Address review comments
5. Get approval from 2+ reviewers
6. Merge to develop branch

---

## Troubleshooting

### Common Issues

**Issue**: Java version not recognized
```bash
# Solution: Check JAVA_HOME
echo $JAVA_HOME
# Should point to Java 21 installation

# Set JAVA_HOME
export JAVA_HOME=/path/to/java21
```

**Issue**: Maven build fails with dependency error
```bash
# Solution: Clear Maven cache
rm -rf ~/.m2/repository
mvn clean install
```

**Issue**: Docker containers won't start
```bash
# Solution: Check Docker daemon
docker ps

# Restart Docker
docker-compose down
docker-compose up -d
```

**Issue**: Port already in use
```bash
# Solution: Find process using port
lsof -i :5432  # PostgreSQL
lsof -i :27017  # MongoDB
lsof -i :9200  # Elasticsearch

# Kill process
kill -9 <PID>
```

**Issue**: Git merge conflicts
```bash
# Solution: Resolve conflicts manually
git status  # See conflicted files
# Edit files to resolve conflicts
git add .
git commit -m "Resolve merge conflicts"
```

---

## Next Steps

### Immediate Actions (Before Development Starts)

1. **Setup Development Environment**:
   - [ ] Install Java 21
   - [ ] Install Maven 3.9+
   - [ ] Install Git
   - [ ] Install Docker
   - [ ] Install IDE

2. **Clone Repository**:
   - [ ] Clone repository
   - [ ] Verify all branches
   - [ ] Create feature branch

3. **Verify Setup**:
   - [ ] Run `mvn clean install`
   - [ ] Run `mvn test`
   - [ ] Start Docker services
   - [ ] Verify database connectivity

4. **IDE Configuration**:
   - [ ] Import project
   - [ ] Configure JDK
   - [ ] Configure Maven
   - [ ] Install plugins
   - [ ] Import code style

### First Development Tasks

1. **Sprint 1-1 Tasks**:
   - [ ] Task 1.1.1: Git Repository Setup
   - [ ] Task 1.1.2: Maven Parent POM
   - [ ] Task 1.1.3: Architecture Decision Records
   - [ ] Task 1.1.4: C4 Architecture Diagrams
   - [ ] Task 1.1.5: Development Environment Guide

2. **Sprint 1-2 Tasks**:
   - [ ] Create shared-domain module
   - [ ] Create shared-exceptions module
   - [ ] Create shared-utils module
   - [ ] Create shared-logging module
   - [ ] Setup testing framework

---

## Useful Commands Reference

### Maven Commands
```bash
mvn clean                    # Clean build directory
mvn compile                  # Compile source code
mvn test                     # Run tests
mvn install                  # Build and install
mvn package                  # Create JAR/WAR
mvn clean install -DskipTests  # Build without tests
mvn clean verify sonar:sonar # Run SonarQube analysis
mvn jacoco:report           # Generate coverage report
```

### Git Commands
```bash
git status                   # Check status
git add .                    # Stage changes
git commit -m "message"      # Commit changes
git push origin branch       # Push to remote
git pull origin branch       # Pull from remote
git checkout -b branch       # Create new branch
git merge branch             # Merge branch
git log --oneline            # View commit history
```

### Docker Commands
```bash
docker-compose up -d         # Start services
docker-compose down          # Stop services
docker-compose ps            # List services
docker-compose logs -f       # View logs
docker-compose restart       # Restart services
docker ps                    # List running containers
docker logs container-name   # View container logs
```

---

## Resources & Documentation

**Official Documentation**:
- [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Git Documentation](https://git-scm.com/doc)
- [Docker Documentation](https://docs.docker.com/)

**Project Documentation**:
- Architecture: `docs/architecture/`
- API Specification: `docs/api/`
- Development Guides: `docs/guides/`
- Code Examples: `docs/examples/`

---

## Support & Help

**Getting Help**:
- Slack channel: #development
- Email: development-team@company.com
- Wiki: https://github.com/your-org/snowrepo-springboot/wiki
- Issues: https://github.com/your-org/snowrepo-springboot/issues

---

**Code Setup Complete!** 🚀

**Status**: ✅ Ready for Development  
**Last Updated**: July 12, 2026  
**Next**: Start Sprint 1-1 Development on July 15, 2026
