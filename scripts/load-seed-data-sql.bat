@echo off
REM ============================================================================
REM Snowrepo Seed Data Loader Script (Windows)
REM Loads sample data into all microservice databases
REM ============================================================================

setlocal enabledelayexpansion

REM Configuration
set CONTAINER_NAME=itsm-postgres
set DB_USER=admin
set DB_PASSWORD=secret
set SCRIPT_PATH=%~dp0seed-data.sql

echo.
echo ============================================================================
echo Snowrepo Seed Data Loader
echo ============================================================================
echo.

REM Check if Docker is running
echo [INFO] Checking Docker status...
docker ps >nul 2>&1
if errorlevel 1 (
  echo [ERROR] Docker is not running. Please start Docker and try again.
  exit /b 1
)

REM Check if PostgreSQL container exists
echo [INFO] Checking PostgreSQL container...
docker ps -a --filter "name=%CONTAINER_NAME%" --format "{{.Names}}" | findstr /R "^%CONTAINER_NAME%$" >nul
if errorlevel 1 (
  echo [ERROR] PostgreSQL container '%CONTAINER_NAME%' not found.
  exit /b 1
)

REM Check if container is running
docker ps --filter "name=%CONTAINER_NAME%" --format "{{.Names}}" | findstr /R "^%CONTAINER_NAME%$" >nul
if errorlevel 1 (
  echo [WARNING] Container is not running. Starting it...
  docker start %CONTAINER_NAME%
  timeout /t 3 /nobreak
)

REM Check if databases exist
echo [INFO] Checking databases...
docker exec %CONTAINER_NAME% psql -U %DB_USER% -lqt | findstr itsm_db >nul
if errorlevel 1 (
  echo [ERROR] Databases not found. Please run init-databases script first.
  exit /b 1
)

REM Execute seed data script
echo [INFO] Loading seed data...
echo [INFO] Script: %SCRIPT_PATH%
echo.

docker exec -i %CONTAINER_NAME% psql -U %DB_USER% -f - < "%SCRIPT_PATH%"

if errorlevel 1 (
  echo.
  echo [ERROR] Seed data loading failed!
  exit /b 1
)

REM Verify data
echo.
echo [INFO] Verifying seed data...
echo.

for /f "tokens=*" %%A in ('docker exec %CONTAINER_NAME% psql -U %DB_USER% -d itsm_db -t -c "SELECT COUNT(*) FROM incidents"') do set ITSM_COUNT=%%A
for /f "tokens=*" %%A in ('docker exec %CONTAINER_NAME% psql -U %DB_USER% -d cmdb_db -t -c "SELECT COUNT(*) FROM configuration_items"') do set CMDB_COUNT=%%A
for /f "tokens=*" %%A in ('docker exec %CONTAINER_NAME% psql -U %DB_USER% -d itom_db -t -c "SELECT COUNT(*) FROM discovered_assets"') do set ITOM_COUNT=%%A
for /f "tokens=*" %%A in ('docker exec %CONTAINER_NAME% psql -U %DB_USER% -d itam_db -t -c "SELECT COUNT(*) FROM hardware_assets"') do set ITAM_COUNT=%%A

echo [SUCCESS] Seed data loaded successfully!
echo.
echo ============================================================================
echo Seed Data Summary
echo ============================================================================
echo.
echo Databases Populated:
echo   - itsm_db.incidents: %ITSM_COUNT% records
echo   - cmdb_db.configuration_items: %CMDB_COUNT% records
echo   - itom_db.discovered_assets: %ITOM_COUNT% records
echo   - itam_db.hardware_assets: %ITAM_COUNT% records
echo.
echo Test Users:
echo   - admin@example.com (Admin)
echo   - itsm.manager@example.com (ITSM Manager)
echo   - cmdb.admin@example.com (CMDB Admin)
echo   - ops.manager@example.com (Operations Manager)
echo.
echo Next Steps:
echo   1. Build backend microservices
echo   2. Start backend services
echo   3. Start frontend application
echo   4. Login with test credentials
echo.
echo ============================================================================
echo.

exit /b 0
