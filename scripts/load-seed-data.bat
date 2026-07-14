@echo off
REM Seed Data Loader Script for Windows
REM Loads seed data into all microservices

setlocal enabledelayedexpansion

echo.
echo ==========================================
echo Loading Seed Data for All Services
echo ==========================================
echo.

REM Configuration
set ITSM_URL=http://localhost:8001/api/v1
set CMDB_URL=http://localhost:8000/api/v1
set ITOM_URL=http://localhost:8002/api/v1
set ITAM_URL=http://localhost:8003/api/v1

REM Check if curl is available
where curl >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: curl is not installed or not in PATH
    echo Please install curl or add it to your PATH
    exit /b 1
)

REM Function to check service
echo Checking services...

curl -s %ITSM_URL%/incidents >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] ITSM Service is running
) else (
    echo [ERROR] ITSM Service is not running on %ITSM_URL%
    exit /b 1
)

curl -s %CMDB_URL%/cis >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] CMDB Service is running
) else (
    echo [ERROR] CMDB Service is not running on %CMDB_URL%
    exit /b 1
)

curl -s %ITOM_URL%/assets >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] ITOM Service is running
) else (
    echo [ERROR] ITOM Service is not running on %ITOM_URL%
    exit /b 1
)

curl -s %ITAM_URL%/assets >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo [OK] ITAM Service is running
) else (
    echo [ERROR] ITAM Service is not running on %ITAM_URL%
    exit /b 1
)

echo.
echo Loading ITSM Incidents...

REM Load ITSM Incidents
curl -X POST %ITSM_URL%/incidents ^
  -H "Content-Type: application/json" ^
  -d "{\"incidentNumber\":\"INC-001\",\"title\":\"Production Database Down\",\"description\":\"Critical production database is not responding\",\"priority\":\"CRITICAL\",\"reporter\":\"admin@example.com\"}" >nul 2>&1
echo [OK] Created incident: INC-001

curl -X POST %ITSM_URL%/incidents ^
  -H "Content-Type: application/json" ^
  -d "{\"incidentNumber\":\"INC-002\",\"title\":\"Email Server Slow\",\"description\":\"Email server responding slowly to requests\",\"priority\":\"HIGH\",\"reporter\":\"admin@example.com\"}" >nul 2>&1
echo [OK] Created incident: INC-002

curl -X POST %ITSM_URL%/incidents ^
  -H "Content-Type: application/json" ^
  -d "{\"incidentNumber\":\"INC-003\",\"title\":\"VPN Connection Issues\",\"description\":\"Users unable to connect to corporate VPN\",\"priority\":\"HIGH\",\"reporter\":\"ops.manager@example.com\"}" >nul 2>&1
echo [OK] Created incident: INC-003

curl -X POST %ITSM_URL%/incidents ^
  -H "Content-Type: application/json" ^
  -d "{\"incidentNumber\":\"INC-004\",\"title\":\"Printer Not Working\",\"description\":\"Office printer on 3rd floor not responding\",\"priority\":\"MEDIUM\",\"reporter\":\"admin@example.com\"}" >nul 2>&1
echo [OK] Created incident: INC-004

curl -X POST %ITSM_URL%/incidents ^
  -H "Content-Type: application/json" ^
  -d "{\"incidentNumber\":\"INC-005\",\"title\":\"Network Latency\",\"description\":\"High network latency observed in main office\",\"priority\":\"MEDIUM\",\"reporter\":\"ops.manager@example.com\"}" >nul 2>&1
echo [OK] Created incident: INC-005

echo.
echo Loading CMDB Configuration Items...

REM Load CMDB Items
curl -X POST %CMDB_URL%/cis ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Production-DB-01\",\"type\":\"Database\",\"description\":\"Primary production database server\",\"owner\":\"cmdb.admin@example.com\"}" >nul 2>&1
echo [OK] Created CI: Production-DB-01

curl -X POST %CMDB_URL%/cis ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Web-Server-01\",\"type\":\"Server\",\"description\":\"Primary web application server\",\"owner\":\"cmdb.admin@example.com\"}" >nul 2>&1
echo [OK] Created CI: Web-Server-01

curl -X POST %CMDB_URL%/cis ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Web-Server-02\",\"type\":\"Server\",\"description\":\"Secondary web application server\",\"owner\":\"cmdb.admin@example.com\"}" >nul 2>&1
echo [OK] Created CI: Web-Server-02

curl -X POST %CMDB_URL%/cis ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Email-Server\",\"type\":\"Server\",\"description\":\"Corporate email server\",\"owner\":\"cmdb.admin@example.com\"}" >nul 2>&1
echo [OK] Created CI: Email-Server

curl -X POST %CMDB_URL%/cis ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Backup-Storage\",\"type\":\"Storage\",\"description\":\"Backup storage system\",\"owner\":\"cmdb.admin@example.com\"}" >nul 2>&1
echo [OK] Created CI: Backup-Storage

curl -X POST %CMDB_URL%/cis ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Network-Switch-01\",\"type\":\"Network Device\",\"description\":\"Core network switch\",\"owner\":\"ops.manager@example.com\"}" >nul 2>&1
echo [OK] Created CI: Network-Switch-01

curl -X POST %CMDB_URL%/cis ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Firewall-01\",\"type\":\"Security Device\",\"description\":\"Primary firewall\",\"owner\":\"ops.manager@example.com\"}" >nul 2>&1
echo [OK] Created CI: Firewall-01

curl -X POST %CMDB_URL%/cis ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"VPN-Gateway\",\"type\":\"Network Device\",\"description\":\"VPN gateway for remote access\",\"owner\":\"ops.manager@example.com\"}" >nul 2>&1
echo [OK] Created CI: VPN-Gateway

echo.
echo Loading ITOM Discovered Assets...

REM Load ITOM Assets
curl -X POST %ITOM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Server-01\",\"assetType\":\"Server\",\"description\":\"Production web server\",\"ipAddress\":\"192.168.1.10\",\"hostname\":\"server-01.example.com\"}" >nul 2>&1
echo [OK] Created asset: Server-01

curl -X POST %ITOM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Server-02\",\"assetType\":\"Server\",\"description\":\"Production application server\",\"ipAddress\":\"192.168.1.11\",\"hostname\":\"server-02.example.com\"}" >nul 2>&1
echo [OK] Created asset: Server-02

curl -X POST %ITOM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Server-03\",\"assetType\":\"Server\",\"description\":\"Database server\",\"ipAddress\":\"192.168.1.12\",\"hostname\":\"db-server.example.com\"}" >nul 2>&1
echo [OK] Created asset: Server-03

curl -X POST %ITOM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Workstation-01\",\"assetType\":\"Workstation\",\"description\":\"Admin workstation\",\"ipAddress\":\"192.168.1.100\",\"hostname\":\"admin-ws.example.com\"}" >nul 2>&1
echo [OK] Created asset: Workstation-01

curl -X POST %ITOM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Printer-01\",\"assetType\":\"Printer\",\"description\":\"Office printer\",\"ipAddress\":\"192.168.1.50\",\"hostname\":\"printer-01.example.com\"}" >nul 2>&1
echo [OK] Created asset: Printer-01

curl -X POST %ITOM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Switch-01\",\"assetType\":\"Network Switch\",\"description\":\"Core network switch\",\"ipAddress\":\"192.168.1.1\",\"hostname\":\"switch-01.example.com\"}" >nul 2>&1
echo [OK] Created asset: Switch-01

curl -X POST %ITOM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Firewall-01\",\"assetType\":\"Firewall\",\"description\":\"Primary firewall\",\"ipAddress\":\"192.168.1.2\",\"hostname\":\"firewall-01.example.com\"}" >nul 2>&1
echo [OK] Created asset: Firewall-01

curl -X POST %ITOM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Router-01\",\"assetType\":\"Router\",\"description\":\"Core router\",\"ipAddress\":\"10.0.0.1\",\"hostname\":\"router-01.example.com\"}" >nul 2>&1
echo [OK] Created asset: Router-01

echo.
echo Loading ITAM Hardware Assets...

REM Load ITAM Hardware
curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-001\",\"name\":\"Dell XPS 15\",\"assetType\":\"Laptop\",\"manufacturer\":\"Dell\",\"model\":\"XPS 15\",\"serialNumber\":\"DELL-XPS-001\",\"purchaseCost\":2500.00}" >nul 2>&1
echo [OK] Created hardware: HW-001

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-002\",\"name\":\"MacBook Pro 16\",\"assetType\":\"Laptop\",\"manufacturer\":\"Apple\",\"model\":\"MacBook Pro 16\",\"serialNumber\":\"APPLE-MBP-002\",\"purchaseCost\":3500.00}" >nul 2>&1
echo [OK] Created hardware: HW-002

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-003\",\"name\":\"HP ProBook 450\",\"assetType\":\"Laptop\",\"manufacturer\":\"HP\",\"model\":\"ProBook 450\",\"serialNumber\":\"HP-PB-003\",\"purchaseCost\":1200.00}" >nul 2>&1
echo [OK] Created hardware: HW-003

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-004\",\"name\":\"Lenovo ThinkPad\",\"assetType\":\"Laptop\",\"manufacturer\":\"Lenovo\",\"model\":\"ThinkPad X1\",\"serialNumber\":\"LENOVO-TP-004\",\"purchaseCost\":1500.00}" >nul 2>&1
echo [OK] Created hardware: HW-004

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-005\",\"name\":\"Dell Monitor 27\",\"assetType\":\"Monitor\",\"manufacturer\":\"Dell\",\"model\":\"U2720Q\",\"serialNumber\":\"DELL-MON-005\",\"purchaseCost\":450.00}" >nul 2>&1
echo [OK] Created hardware: HW-005

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-006\",\"name\":\"Logitech Keyboard\",\"assetType\":\"Keyboard\",\"manufacturer\":\"Logitech\",\"model\":\"MX Keys\",\"serialNumber\":\"LOGI-KB-006\",\"purchaseCost\":99.00}" >nul 2>&1
echo [OK] Created hardware: HW-006

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-007\",\"name\":\"Logitech Mouse\",\"assetType\":\"Mouse\",\"manufacturer\":\"Logitech\",\"model\":\"MX Master 3\",\"serialNumber\":\"LOGI-MS-007\",\"purchaseCost\":99.00}" >nul 2>&1
echo [OK] Created hardware: HW-007

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-008\",\"name\":\"Cisco IP Phone\",\"assetType\":\"Phone\",\"manufacturer\":\"Cisco\",\"model\":\"8861\",\"serialNumber\":\"CISCO-PH-008\",\"purchaseCost\":350.00}" >nul 2>&1
echo [OK] Created hardware: HW-008

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-009\",\"name\":\"Apple iPad Pro\",\"assetType\":\"Tablet\",\"manufacturer\":\"Apple\",\"model\":\"iPad Pro 12.9\",\"serialNumber\":\"APPLE-IPD-009\",\"purchaseCost\":1200.00}" >nul 2>&1
echo [OK] Created hardware: HW-009

curl -X POST %ITAM_URL%/assets ^
  -H "Content-Type: application/json" ^
  -d "{\"assetTag\":\"HW-010\",\"name\":\"Samsung 4K Monitor\",\"assetType\":\"Monitor\",\"manufacturer\":\"Samsung\",\"model\":\"LU28E590DS\",\"serialNumber\":\"SAMSUNG-MON-010\",\"purchaseCost\":350.00}" >nul 2>&1
echo [OK] Created hardware: HW-010

echo.
echo ==========================================
echo Seed Data Loaded Successfully!
echo ==========================================
echo.
echo Summary:
echo   - ITSM Incidents: 5
echo   - CMDB Items: 8
echo   - ITOM Assets: 8
echo   - ITAM Hardware: 10
echo   - Total Records: 31
echo.
echo You can now login with:
echo   Email: admin@example.com
echo   Password: Admin@123456
echo.

pause
