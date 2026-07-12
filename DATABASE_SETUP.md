# Database Infrastructure Setup
## PostgreSQL & MongoDB with Docker

**Status**: ✅ COMPLETE  
**Date**: July 12, 2026

---

## Overview

Database infrastructure using Docker containers for PostgreSQL and MongoDB as per requirements.

---

## PostgreSQL Setup

### Docker Configuration

```yaml
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
  healthcheck:
    test: ["CMD-SHELL", "pg_isready -U snowrepo"]
    interval: 10s
    timeout: 5s
    retries: 5
```

### Databases

- **snowrepo**: Main application database
- **snowrepo_test**: Testing database

### Tables

- incidents
- users
- cmdb_items
- audit_logs
- api_keys

### Connection

```
Host: localhost
Port: 5432
Database: snowrepo
User: snowrepo
Password: snowrepo123
```

---

## MongoDB Setup

### Docker Configuration

```yaml
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
  healthcheck:
    test: ["CMD", "mongosh", "--eval", "db.adminCommand('ping')"]
    interval: 10s
    timeout: 5s
    retries: 5
```

### Collections

- incidents
- users
- audit_logs
- notifications
- search_index

### Connection

```
Host: localhost
Port: 27017
Database: snowrepo
User: snowrepo
Password: snowrepo123
```

---

## Docker Compose

Start all services:

```bash
docker-compose up -d
```

Stop services:

```bash
docker-compose down
```

View logs:

```bash
docker-compose logs -f
```

---

## Database Initialization

### PostgreSQL

```sql
CREATE DATABASE snowrepo;
CREATE USER snowrepo WITH PASSWORD 'snowrepo123';
GRANT ALL PRIVILEGES ON DATABASE snowrepo TO snowrepo;
```

### MongoDB

```javascript
db.createUser({
  user: "snowrepo",
  pwd: "snowrepo123",
  roles: ["readWrite"]
});
```

---

## Backup & Restore

### PostgreSQL Backup

```bash
docker exec snowrepo-postgres pg_dump -U snowrepo snowrepo > backup.sql
```

### MongoDB Backup

```bash
docker exec snowrepo-mongodb mongodump --out /backup
```

---

**Status**: ✅ READY FOR USE

**Last Updated**: July 12, 2026
