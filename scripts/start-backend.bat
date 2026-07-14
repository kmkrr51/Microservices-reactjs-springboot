@echo off
REM ============================================================================
REM Snowrepo Backend Services Startup Script (Windows)
REM Starts all microservices in separate command windows
REM ============================================================================

setlocal enabledelayexpansion

REM Configuration
set PROJECT_DIR=d:\workspace\Snowrepo-springboot\Snowrepo-springboot
set JAVA_OPTS=-Xmx512m -Xms256m

echo.
echo ============================================================================
echo Snowrepo Backend Services Startup
echo ============================================================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
  echo [ERROR] Java is not installed or not in PATH
  exit /b 1
)

echo [INFO] Starting backend microservices...
echo.

REM Start API Gateway (Port 8000)
echo [INFO] Starting API Gateway on port 8000...
start "API Gateway" cmd /k "cd /d %PROJECT_DIR% && java %JAVA_OPTS% -jar api-gateway\target\api-gateway-1.0.0-SNAPSHOT.jar"
timeout /t 3 /nobreak

REM Start CMDB Service (Port 8001)
echo [INFO] Starting CMDB Service on port 8001...
start "CMDB Service" cmd /k "cd /d %PROJECT_DIR% && java %JAVA_OPTS% -jar cmdb\target\cmdb-1.0.0-SNAPSHOT.jar"
timeout /t 3 /nobreak

REM Start ITSM Service (Port 8002)
echo [INFO] Starting ITSM Service on port 8002...
start "ITSM Service" cmd /k "cd /d %PROJECT_DIR% && java %JAVA_OPTS% -jar itsm\target\itsm-1.0.0-SNAPSHOT.jar"
timeout /t 3 /nobreak

REM Start ITOM Service (Port 8003)
echo [INFO] Starting ITOM Service on port 8003...
start "ITOM Service" cmd /k "cd /d %PROJECT_DIR% && java %JAVA_OPTS% -jar itom\target\itom-1.0.0-SNAPSHOT.jar"
timeout /t 3 /nobreak

REM Start ITAM Service (Port 8004)
echo [INFO] Starting ITAM Service on port 8004...
start "ITAM Service" cmd /k "cd /d %PROJECT_DIR% && java %JAVA_OPTS% -jar itam\target\itam-1.0.0-SNAPSHOT.jar"
timeout /t 3 /nobreak

echo.
echo ============================================================================
echo Backend Services Started
echo ============================================================================
echo.
echo Services:
echo   - API Gateway: http://localhost:8000
echo   - CMDB Service: http://localhost:8001
echo   - ITSM Service: http://localhost:8002
echo   - ITOM Service: http://localhost:8003
echo   - ITAM Service: http://localhost:8004
echo.
echo Keycloak: http://localhost:8080
echo PostgreSQL: localhost:5432
echo Kafka: localhost:9092
echo.
echo Waiting for services to start (30 seconds)...
timeout /t 30 /nobreak

echo.
echo [INFO] Checking service health...
echo.

REM Check API Gateway
echo [INFO] Checking API Gateway...
curl -s http://localhost:8000/actuator/health >nul
if errorlevel 1 (
  echo [WARNING] API Gateway may still be starting...
) else (
  echo [SUCCESS] API Gateway is running
)

echo.
echo ============================================================================
echo Backend startup complete. Check individual windows for logs.
echo ============================================================================
echo.

pause
