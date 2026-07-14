@echo off
REM ============================================================================
REM Snowrepo Database Initialization Script (Windows)
REM Initializes all databases and tables in PostgreSQL Docker container
REM ============================================================================

setlocal enabledelayexpansion

REM Configuration
set CONTAINER_NAME=itsm-postgres
set DB_USER=admin
set DB_PASSWORD=secret
set SCRIPT_PATH=%~dp0init-databases.sql

echo.
echo ============================================================================
echo Snowrepo Database Initialization
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

REM Execute initialization script
echo [INFO] Running database initialization script...
echo [INFO] Script: %SCRIPT_PATH%
echo.

docker exec -i %CONTAINER_NAME% psql -U %DB_USER% -f - < "%SCRIPT_PATH%"

if errorlevel 1 (
  echo.
  echo [ERROR] Database initialization failed!
  exit /b 1
)

echo.
echo [SUCCESS] Database initialization completed successfully!
echo.
echo ============================================================================
echo Databases Created:
echo   - itsm_db (Incident Management)
echo   - cmdb_db (Configuration Management)
echo   - itom_db (IT Operations Management)
echo   - itam_db (IT Asset Management)
echo ============================================================================
echo.
echo Tables Created:
echo   - itsm_db.incidents
echo   - cmdb_db.configuration_items
echo   - itom_db.discovered_assets
echo   - itam_db.hardware_assets
echo ============================================================================
echo.

exit /b 0
