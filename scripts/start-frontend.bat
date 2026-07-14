@echo off
REM ============================================================================
REM Snowrepo Frontend Startup Script (Windows)
REM Starts the React frontend application
REM ============================================================================

setlocal enabledelayexpansion

REM Configuration
set PROJECT_DIR=d:\workspace\Snowrepo-springboot\Snowrepo-springboot\frontend
set PORT=5173

echo.
echo ============================================================================
echo Snowrepo Frontend Startup
echo ============================================================================
echo.

REM Check if Node.js is installed
node --version >nul 2>&1
if errorlevel 1 (
  echo [ERROR] Node.js is not installed or not in PATH
  echo [INFO] Please install Node.js from https://nodejs.org/
  exit /b 1
)

REM Check if npm is installed
npm --version >nul 2>&1
if errorlevel 1 (
  echo [ERROR] npm is not installed or not in PATH
  exit /b 1
)

echo [INFO] Node.js version:
node --version
echo.
echo [INFO] npm version:
npm --version
echo.

REM Check if node_modules exists
if not exist "%PROJECT_DIR%\node_modules" (
  echo [INFO] Installing dependencies...
  cd /d "%PROJECT_DIR%"
  call npm install
  if errorlevel 1 (
    echo [ERROR] Failed to install dependencies
    exit /b 1
  )
)

echo.
echo [INFO] Starting frontend application on port %PORT%...
echo.

REM Start the development server
cd /d "%PROJECT_DIR%"
call npm run dev

REM If we reach here, the dev server has stopped
echo.
echo [INFO] Frontend application stopped
echo.

pause
