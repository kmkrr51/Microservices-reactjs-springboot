# Start Services Guide

This guide provides instructions for starting all backend microservices and the frontend application.

## Prerequisites

- Java 17 installed and in PATH
- Node.js and npm installed
- Docker running with all services (PostgreSQL, Kafka, Zookeeper, Keycloak)
- All JAR files built (in target directories)
- Seed data loaded into databases

## Service Ports

| Service | Port | URL |
|---------|------|-----|
| API Gateway | 8000 | http://localhost:8000 |
| CMDB Service | 8001 | http://localhost:8001 |
| ITSM Service | 8002 | http://localhost:8002 |
| ITOM Service | 8003 | http://localhost:8003 |
| ITAM Service | 8004 | http://localhost:8004 |
| Frontend | 5173 | http://localhost:5173 |
| Keycloak | 8080 | http://localhost:8080 |

## Starting Backend Services

### Method 1: Individual Command Windows (Windows)

Open 5 separate command windows and run each command:

**Window 1 - API Gateway**:
```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
java -Xmx512m -Xms256m -jar api-gateway\target\api-gateway-1.0.0-SNAPSHOT.jar
```

**Window 2 - CMDB Service**:
```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
java -Xmx512m -Xms256m -jar cmdb\target\cmdb-1.0.0-SNAPSHOT.jar
```

**Window 3 - ITSM Service**:
```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
java -Xmx512m -Xms256m -jar itsm\target\itsm-1.0.0-SNAPSHOT.jar
```

**Window 4 - ITOM Service**:
```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
java -Xmx512m -Xms256m -jar itom\target\itom-1.0.0-SNAPSHOT.jar
```

**Window 5 - ITAM Service**:
```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
java -Xmx512m -Xms256m -jar itam\target\itam-1.0.0-SNAPSHOT.jar
```

### Method 2: Batch Script (Windows)

Run the provided batch script:
```bash
scripts\start-backend.bat
```

This will open separate command windows for each service.

### Method 3: Linux/Mac

Open 5 separate terminal windows and run:

```bash
# Terminal 1 - API Gateway
cd /path/to/Snowrepo-springboot
java -Xmx512m -Xms256m -jar api-gateway/target/api-gateway-1.0.0-SNAPSHOT.jar

# Terminal 2 - CMDB Service
java -Xmx512m -Xms256m -jar cmdb/target/cmdb-1.0.0-SNAPSHOT.jar

# Terminal 3 - ITSM Service
java -Xmx512m -Xms256m -jar itsm/target/itsm-1.0.0-SNAPSHOT.jar

# Terminal 4 - ITOM Service
java -Xmx512m -Xms256m -jar itom/target/itom-1.0.0-SNAPSHOT.jar

# Terminal 5 - ITAM Service
java -Xmx512m -Xms256m -jar itam/target/itam-1.0.0-SNAPSHOT.jar
```

## Starting Frontend Application

### Method 1: Command Line (Windows)

```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot\frontend
npm install
npm run dev
```

### Method 2: Batch Script (Windows)

```bash
scripts\start-frontend.bat
```

### Method 3: Linux/Mac

```bash
cd /path/to/Snowrepo-springboot/frontend
npm install
npm run dev
```

## Verification

### Check Backend Services

Once services are running, verify they're healthy:

```bash
# API Gateway
curl http://localhost:8000/actuator/health

# CMDB Service
curl http://localhost:8001/actuator/health

# ITSM Service
curl http://localhost:8002/actuator/health

# ITOM Service
curl http://localhost:8003/actuator/health

# ITAM Service
curl http://localhost:8004/actuator/health
```

Expected response:
```json
{
  "status": "UP"
}
```

### Check Frontend

Open browser and navigate to:
```
http://localhost:5173
```

You should see the login page.

## Login Credentials

Use any of these test users:

| Email | Password | Role |
|-------|----------|------|
| admin@example.com | Admin@123456 | Admin |
| itsm.manager@example.com | ITSM@123456 | ITSM Manager |
| cmdb.admin@example.com | CMDB@123456 | CMDB Admin |
| ops.manager@example.com | Ops@123456 | Operations Manager |

## Service Startup Order

Recommended startup order:

1. **Docker Services** (already running)
   - PostgreSQL
   - Kafka
   - Zookeeper
   - Keycloak

2. **Backend Services** (start in any order, but API Gateway first is recommended)
   - API Gateway (port 8000)
   - CMDB Service (port 8001)
   - ITSM Service (port 8002)
   - ITOM Service (port 8003)
   - ITAM Service (port 8004)

3. **Frontend** (start after backend is ready)
   - React Frontend (port 5173)

## Startup Time

- Docker services: Already running
- Each backend service: 20-30 seconds to start
- Frontend: 10-15 seconds to start
- **Total startup time**: ~3-5 minutes for all services

## Logs

### Backend Service Logs

Logs are printed to the command window where the service is running. Look for:
- `Started [ServiceName]` - Service started successfully
- `Tomcat started on port` - Web server ready
- `Hibernate: create table` - Database initialization

### Frontend Logs

Logs are printed to the terminal where `npm run dev` is running. Look for:
- `VITE v[version]` - Vite dev server started
- `Local: http://localhost:5173/` - Frontend ready

## Troubleshooting

### Port Already in Use

If a port is already in use:

**Windows**:
```bash
netstat -ano | findstr :8000
taskkill /PID [PID] /F
```

**Linux/Mac**:
```bash
lsof -i :8000
kill -9 [PID]
```

### Service Won't Start

1. Check Java version: `java -version` (should be 17+)
2. Check if JAR files exist: `dir cmdb\target\*.jar`
3. Check database connectivity: `docker ps`
4. Check logs in the service window

### Frontend Won't Start

1. Check Node.js: `node --version` (should be 16+)
2. Check npm: `npm --version`
3. Delete node_modules and reinstall: `npm install`
4. Clear npm cache: `npm cache clean --force`

### Database Connection Error

1. Verify PostgreSQL is running: `docker ps | grep postgres`
2. Check database exists: `docker exec itsm-postgres psql -U admin -l`
3. Verify seed data: `docker exec itsm-postgres psql -U admin -d itsm_db -c "SELECT COUNT(*) FROM incidents"`

## Next Steps

1. ✅ Start all backend services
2. ✅ Start frontend application
3. Open http://localhost:5173 in browser
4. Login with test credentials
5. Test ITSM, CMDB, ITOM, ITAM modules
6. Create/update incidents and assets
7. Monitor logs for any errors

## Performance Tips

- Allocate more memory if needed: `-Xmx1024m -Xms512m`
- Use SSD for better performance
- Close unnecessary applications to free up resources
- Monitor CPU and memory usage during startup

## Stopping Services

To stop services:

1. **Backend**: Press `Ctrl+C` in each service window
2. **Frontend**: Press `Ctrl+C` in the npm dev terminal
3. **Docker**: `docker stop [container-name]` or `docker-compose down`

## Support

For issues:
1. Check service logs for error messages
2. Verify all prerequisites are installed
3. Ensure Docker services are running
4. Check database connectivity
5. Review application configuration files
