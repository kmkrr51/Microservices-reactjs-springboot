@echo off
REM ============================================================================
REM Snowrepo Services Stop Script (Windows)
REM Stops all backend microservices and frontend application
REM ============================================================================

setlocal enabledelayexpansion

echo.
echo ============================================================================
echo Snowrepo Services Stop
echo ============================================================================
echo.

REM Stop backend services
echo [INFO] Stopping backend services...
echo.

REM Kill Java processes running Spring Boot services
echo [INFO] Stopping API Gateway (port 8000)...
netstat -ano | findstr :8000 | for /f "tokens=5" %%a in ('findstr :8000') do taskkill /PID %%a /F 2>nul

echo [INFO] Stopping CMDB Service (port 8001)...
netstat -ano | findstr :8001 | for /f "tokens=5" %%a in ('findstr :8001') do taskkill /PID %%a /F 2>nul

echo [INFO] Stopping ITSM Service (port 8002)...
netstat -ano | findstr :8002 | for /f "tokens=5" %%a in ('findstr :8002') do taskkill /PID %%a /F 2>nul

echo [INFO] Stopping ITOM Service (port 8003)...
netstat -ano | findstr :8003 | for /f "tokens=5" %%a in ('findstr :8003') do taskkill /PID %%a /F 2>nul

echo [INFO] Stopping ITAM Service (port 8004)...
netstat -ano | findstr :8004 | for /f "tokens=5" %%a in ('findstr :8004') do taskkill /PID %%a /F 2>nul

echo.
echo [INFO] Stopping frontend application (port 5173)...
netstat -ano | findstr :5173 | for /f "tokens=5" %%a in ('findstr :5173') do taskkill /PID %%a /F 2>nul

echo.
echo [SUCCESS] All services stopped!
echo.
echo ============================================================================
echo Service Status
echo ============================================================================
echo.

REM Check if ports are free
echo [INFO] Checking port availability...
echo.

netstat -ano | findstr :8000 >nul
if errorlevel 1 (
  echo [OK] Port 8000 (API Gateway): FREE
) else (
  echo [WARNING] Port 8000 (API Gateway): IN USE
)

netstat -ano | findstr :8001 >nul
if errorlevel 1 (
  echo [OK] Port 8001 (CMDB): FREE
) else (
  echo [WARNING] Port 8001 (CMDB): IN USE
)

netstat -ano | findstr :8002 >nul
if errorlevel 1 (
  echo [OK] Port 8002 (ITSM): FREE
) else (
  echo [WARNING] Port 8002 (ITSM): IN USE
)

netstat -ano | findstr :8003 >nul
if errorlevel 1 (
  echo [OK] Port 8003 (ITOM): FREE
) else (
  echo [WARNING] Port 8003 (ITOM): IN USE
)

netstat -ano | findstr :8004 >nul
if errorlevel 1 (
  echo [OK] Port 8004 (ITAM): FREE
) else (
  echo [WARNING] Port 8004 (ITAM): IN USE
)

netstat -ano | findstr :5173 >nul
if errorlevel 1 (
  echo [OK] Port 5173 (Frontend): FREE
) else (
  echo [WARNING] Port 5173 (Frontend): IN USE
)

echo.
echo ============================================================================
echo Docker Services (Still Running)
echo ============================================================================
echo.
echo The following Docker services are still running:
echo   - PostgreSQL (5432)
echo   - Kafka (9092)
echo   - Zookeeper (2181)
echo   - Keycloak (8080)
echo.
echo To stop Docker services, run:
echo   docker stop [container-name]
echo   or
echo   docker-compose down
echo.
echo ============================================================================
echo.

pause
