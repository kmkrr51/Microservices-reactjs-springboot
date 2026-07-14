# CORS Issue Troubleshooting Guide

## Current Error

```
Access to XMLHttpRequest at 'http://localhost:8089/api/v1/auth/login' 
from origin 'http://localhost:3000' has been blocked by CORS policy: 
Response to preflight request doesn't pass access control check: 
No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

---

## Root Cause

**The API Gateway JAR file has NOT been rebuilt with the new CORS configuration.**

The code changes have been made, but the compiled JAR still has the old configuration.

---

## Solution: Rebuild Backend Services

### Step 1: Clean Build

```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
mvn clean
```

### Step 2: Build All Services

```bash
mvn package -DskipTests -q
```

**This will take 2-3 minutes. Wait for completion.**

### Step 3: Verify JAR Files Created

Check that these JAR files exist:
```
api-gateway/target/api-gateway-1.0.0-SNAPSHOT.jar
itsm/target/itsm-1.0.0-SNAPSHOT.jar
cmdb/target/cmdb-1.0.0-SNAPSHOT.jar
itom/target/itom-1.0.0-SNAPSHOT.jar
itam/target/itam-1.0.0-SNAPSHOT.jar
```

### Step 4: Stop All Running Services

**Kill processes on these ports**:
- 8089 (API Gateway)
- 8016 (ITSM)
- 8015 (CMDB)
- 8018 (ITOM)
- 8017 (ITAM)

**Windows Command**:
```powershell
netstat -ano | findstr :8089
taskkill /PID <PID> /F
```

**Or use the stop script**:
```bash
scripts/stop-services.bat
```

### Step 5: Start API Gateway First

```bash
java -Xmx512m -Xms256m -jar api-gateway\target\api-gateway-1.0.0-SNAPSHOT.jar
```

**Wait for startup message**: `Started ApiGatewayApplication`

### Step 6: Start ITSM Service

```bash
java -Xmx512m -Xms256m -jar itsm\target\itsm-1.0.0-SNAPSHOT.jar
```

**Wait for startup message**: `Started ItsmApplication`

### Step 7: Start Other Services (Optional)

```bash
# CMDB
java -Xmx512m -Xms256m -jar cmdb\target\cmdb-1.0.0-SNAPSHOT.jar

# ITOM
java -Xmx512m -Xms256m -jar itom\target\itom-1.0.0-SNAPSHOT.jar

# ITAM
java -Xmx512m -Xms256m -jar itam\target\itam-1.0.0-SNAPSHOT.jar
```

### Step 8: Reload Frontend

```bash
# Hard refresh in browser: Ctrl+Shift+R
# Or restart dev server: npm run dev
```

---

## Verification

### Check 1: API Gateway is Running

```bash
curl http://localhost:8089/actuator/health
```

**Expected Response**:
```json
{"status":"UP"}
```

### Check 2: CORS Headers are Present

```bash
curl -i -X OPTIONS http://localhost:8089/api/v1/auth/login \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST"
```

**Expected Response Headers**:
```
HTTP/1.1 200 OK
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, PATCH, OPTIONS
Access-Control-Allow-Headers: *
Access-Control-Allow-Credentials: true
Access-Control-Max-Age: 3600
```

### Check 3: Login Endpoint Works

```bash
curl -X POST http://localhost:8089/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -H "Origin: http://localhost:3000" \
  -d '{"email":"admin@example.com","password":"Admin@123456"}'
```

**Expected Response**:
```json
{
  "token": "mock-jwt-token-...",
  "user": {
    "id": "user-...",
    "email": "admin@example.com",
    "name": "admin",
    "role": "ADMIN"
  }
}
```

### Check 4: Browser Console

1. Open **DevTools** (F12)
2. Go to **Network** tab
3. Try to login
4. Look for **OPTIONS** request to `/api/v1/auth/login`
5. Should see **200 OK** (not 404 or CORS error)
6. Check **Response Headers** for `Access-Control-Allow-Origin`

---

## Common Issues & Solutions

### Issue 1: Still Getting CORS Error After Rebuild

**Cause**: JAR file not rebuilt properly

**Solution**:
```bash
# Complete clean rebuild
mvn clean package -DskipTests -q

# Verify JAR was updated
dir api-gateway\target\*.jar
```

### Issue 2: Port 8089 Already in Use

**Cause**: Old API Gateway process still running

**Solution**:
```powershell
# Find process on port 8089
netstat -ano | findstr :8089

# Kill it
taskkill /PID <PID> /F

# Verify port is free
netstat -ano | findstr :8089
```

### Issue 3: "Connection Refused" Error

**Cause**: API Gateway not running

**Solution**:
```bash
# Check if API Gateway is running
curl http://localhost:8089/actuator/health

# If not, start it
java -Xmx512m -Xms256m -jar api-gateway\target\api-gateway-1.0.0-SNAPSHOT.jar
```

### Issue 4: 404 Error on Auth Endpoint

**Cause**: ITSM service not running or auth controller not compiled

**Solution**:
```bash
# Check if ITSM is running
curl http://localhost:8016/actuator/health

# If not, start it
java -Xmx512m -Xms256m -jar itsm\target\itsm-1.0.0-SNAPSHOT.jar
```

---

## What Changed in Code

### 1. API Gateway WebConfig.java
- Changed from servlet-based to reactive `CorsWebFilter`
- Allows localhost:3000 and localhost:5173
- Allows all HTTP methods and headers

### 2. API Gateway application.yml
- Added `spring.web.cors` configuration
- Added auth route: `/api/v1/auth/**` → localhost:8016

### 3. Frontend auth.service.ts
- Changed baseURL from 8080 to 8089

### 4. ITSM AuthController.java (NEW)
- POST `/api/v1/auth/login` endpoint
- GET `/api/v1/auth/me` endpoint

### 5. ITSM DTOs (NEW)
- LoginRequest.java
- AuthResponse.java

---

## Expected Result After Rebuild

✅ **Browser Console**:
- No CORS errors
- OPTIONS request returns 200 OK
- POST request returns 200 OK with token

✅ **Network Tab**:
- OPTIONS /api/v1/auth/login - 200 OK
- POST /api/v1/auth/login - 200 OK
- Response includes CORS headers

✅ **Frontend**:
- Login page works
- Can enter credentials
- Redirects to dashboard after login
- Data loads in dashboard

---

## Quick Rebuild & Test Script

```bash
# 1. Clean and build
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
mvn clean package -DskipTests -q

# 2. Kill old processes
taskkill /F /IM java.exe

# 3. Start API Gateway
start cmd /k "java -Xmx512m -Xms256m -jar api-gateway\target\api-gateway-1.0.0-SNAPSHOT.jar"

# 4. Wait 10 seconds
timeout /t 10

# 5. Start ITSM
start cmd /k "java -Xmx512m -Xms256m -jar itsm\target\itsm-1.0.0-SNAPSHOT.jar"

# 6. Hard refresh frontend (Ctrl+Shift+R)
```

---

## Status

**Code Changes**: ✅ Complete
**Backend Rebuild**: ⏳ REQUIRED
**Frontend Update**: ✅ Complete
**Testing**: ⏳ Pending rebuild

**Next Action**: Run `mvn clean package -DskipTests -q` to rebuild backend
