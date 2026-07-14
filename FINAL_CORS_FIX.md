# Final CORS Fix - Complete Solution

## Problem

```
Access to XMLHttpRequest at 'http://localhost:8089/api/v1/auth/login' 
from origin 'http://localhost:3000' has been blocked by CORS policy
```

## Root Cause

The API Gateway was using the wrong CORS configuration approach for Spring Cloud Gateway.

---

## Solution Applied

### ✅ Fix 1: Updated application.yml with Spring Cloud Gateway CORS

**File**: `api-gateway/src/main/resources/application.yml`

**Changed from**: `spring.web.cors` (servlet-based)
**Changed to**: `spring.cloud.gateway.globalcors` (gateway-based)

```yaml
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "http://localhost:3000"
              - "http://localhost:5173"
            allowedMethods:
              - GET
              - POST
              - PUT
              - DELETE
              - PATCH
              - OPTIONS
            allowedHeaders: "*"
            allowCredentials: true
            maxAge: 3600
```

**Why this works**:
- ✅ Spring Cloud Gateway's native CORS support
- ✅ Handles preflight OPTIONS requests automatically
- ✅ Applies to all routes globally
- ✅ No need for custom filters

---

### ✅ Fix 2: Simplified WebConfig

**File**: `api-gateway/src/main/java/com/snowrepo/gateway/WebConfig.java`

**Before**: Complex CorsWebFilter bean
**After**: Empty configuration class (CORS handled by gateway)

```java
@Configuration
public class WebConfig {
  // CORS is now configured via Spring Cloud Gateway globalcors in application.yml
}
```

**Why this works**:
- ✅ Spring Cloud Gateway handles CORS natively
- ✅ No conflicting filter configurations
- ✅ Cleaner, more maintainable code

---

## How It Works

### Request Flow with CORS

```
Browser (localhost:3000)
    ↓
Sends preflight OPTIONS request:
  OPTIONS /api/v1/auth/login
  Origin: http://localhost:3000
    ↓
API Gateway (8089)
    ↓
Spring Cloud Gateway globalcors filter intercepts
    ↓
Checks if origin matches allowed origins
    ↓
Returns 200 OK with CORS headers:
  Access-Control-Allow-Origin: http://localhost:3000
  Access-Control-Allow-Methods: GET, POST, PUT, DELETE, PATCH, OPTIONS
  Access-Control-Allow-Headers: *
  Access-Control-Allow-Credentials: true
    ↓
Browser receives preflight response
    ↓
Sends actual POST request:
  POST /api/v1/auth/login
  Origin: http://localhost:3000
    ↓
API Gateway routes to ITSM (8016)
    ↓
Returns response with CORS headers
    ↓
Browser receives response
    ↓
✅ LOGIN SUCCESSFUL
```

---

## Implementation Steps

### Step 1: Rebuild Backend

```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
mvn clean package -DskipTests -q
```

**Wait for completion** (2-3 minutes)

### Step 2: Verify JAR Created

```bash
dir api-gateway\target\api-gateway-1.0.0-SNAPSHOT.jar
```

### Step 3: Stop All Services

**Kill processes on ports**: 8089, 8016, 8015, 8018, 8017

```powershell
# Windows
taskkill /F /IM java.exe

# Or use the stop script
scripts/stop-services.bat
```

### Step 4: Start API Gateway

```bash
java -Xmx512m -Xms256m -jar api-gateway\target\api-gateway-1.0.0-SNAPSHOT.jar
```

**Wait for message**: `Started ApiGatewayApplication`

### Step 5: Start ITSM Service

```bash
java -Xmx512m -Xms256m -jar itsm\target\itsm-1.0.0-SNAPSHOT.jar
```

**Wait for message**: `Started ItsmApplication`

### Step 6: Hard Refresh Frontend

```
Ctrl+Shift+R (Windows/Linux)
Cmd+Shift+R (Mac)
```

### Step 7: Test Login

1. Go to login page
2. Enter credentials:
   - Email: admin@example.com
   - Password: Admin@123456
3. Click Login
4. Should see **no CORS errors**
5. Should redirect to dashboard

---

## Verification

### Check 1: API Gateway Health

```bash
curl http://localhost:8089/actuator/health
```

**Expected**:
```json
{"status":"UP"}
```

### Check 2: CORS Preflight

```bash
curl -i -X OPTIONS http://localhost:8089/api/v1/auth/login \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST"
```

**Expected Response**:
```
HTTP/1.1 200 OK
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, PATCH, OPTIONS
Access-Control-Allow-Headers: *
Access-Control-Allow-Credentials: true
Access-Control-Max-Age: 3600
```

### Check 3: Browser DevTools

1. Open **DevTools** (F12)
2. Go to **Network** tab
3. Try to login
4. Look for **OPTIONS** request to `/api/v1/auth/login`
5. Should show **200 OK** (not 404 or CORS error)
6. Check **Response Headers** for `Access-Control-Allow-Origin: http://localhost:3000`

### Check 4: Console

Should see **no errors**, only successful login messages

---

## Files Modified

### 1. `api-gateway/src/main/resources/application.yml`
- Replaced `spring.web.cors` with `spring.cloud.gateway.globalcors`
- Added proper CORS configuration for gateway

### 2. `api-gateway/src/main/java/com/snowrepo/gateway/WebConfig.java`
- Removed CorsWebFilter bean
- Simplified to empty configuration class

### 3. `frontend/src/services/auth.service.ts` (Previous fix)
- Changed baseURL from 8080 to 8089

### 4. `itsm/src/main/java/com/snowrepo/itsm/presentation/AuthController.java` (Previous fix)
- Created auth endpoints

---

## Why This Approach Works

**Spring Cloud Gateway's globalcors**:
- ✅ Designed specifically for reactive gateway applications
- ✅ Automatically handles preflight OPTIONS requests
- ✅ Applies to all routes without manual configuration
- ✅ More efficient than custom filters
- ✅ Standard Spring Cloud Gateway pattern

**Previous approach (CorsWebFilter)**:
- ❌ Designed for servlet applications
- ❌ Can conflict with gateway's route handling
- ❌ Requires manual OPTIONS handling
- ❌ Not the recommended approach for Spring Cloud Gateway

---

## Troubleshooting

### Still Getting CORS Error?

**Cause**: JAR file not rebuilt

**Solution**:
```bash
# Clean rebuild
mvn clean package -DskipTests -q

# Verify JAR timestamp
dir api-gateway\target\api-gateway-1.0.0-SNAPSHOT.jar
```

### Port 8089 Already in Use?

**Solution**:
```powershell
netstat -ano | findstr :8089
taskkill /PID <PID> /F
```

### API Gateway Won't Start?

**Check logs**:
```bash
# Look for error messages in console
# Common issues:
# - Port already in use
# - Java not installed
# - JAR file corrupted
```

---

## Expected Result

✅ **Login page loads without errors**
✅ **Can enter email and password**
✅ **Click login button**
✅ **No CORS errors in console**
✅ **Redirects to dashboard**
✅ **Data loads in dashboard**

---

## Summary

| Component | Status | Details |
|-----------|--------|---------|
| CORS Config | ✅ Fixed | Using Spring Cloud Gateway globalcors |
| Auth Endpoint | ✅ Created | POST /api/v1/auth/login |
| Frontend Port | ✅ Fixed | Changed from 8080 to 8089 |
| Backend Rebuild | ⏳ Required | Run mvn clean package |
| Testing | ⏳ Pending | After rebuild and restart |

---

## Next Action

**Run this command to rebuild and fix the issue**:

```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot && mvn clean package -DskipTests -q
```

**Then restart services and test login.**

**Status**: Ready for rebuild and testing
