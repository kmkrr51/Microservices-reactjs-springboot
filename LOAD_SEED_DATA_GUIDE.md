# Load Seed Data Guide

**Status**: ✅ READY  
**Date**: July 12, 2026

---

## Overview

Three seed data loader scripts have been created for different platforms:
- **Windows**: `scripts/load-seed-data.bat`
- **Linux/Mac**: `scripts/load-seed-data.sh`
- **Cross-platform**: `scripts/load_seed_data.py`

---

## Prerequisites

### All Platforms
1. All 4 Spring Boot services running:
   - CMDB: http://localhost:8000
   - ITSM: http://localhost:8001
   - ITOM: http://localhost:8002
   - ITAM: http://localhost:8003

2. Frontend running (optional for testing):
   - http://localhost:5173

### Windows
- curl installed (usually included in Windows 10+)
- Command Prompt or PowerShell

### Linux/Mac
- bash shell
- curl command-line tool
- Execute permission on script: `chmod +x scripts/load-seed-data.sh`

### Python (All Platforms)
- Python 3.6+
- requests library: `pip install requests`

---

## Method 1: Windows Batch Script

### Step 1: Open Command Prompt
```
Press Win+R, type "cmd", press Enter
```

### Step 2: Navigate to Project
```bash
cd d:\workspace\Snowrepo-springboot\Snowrepo-springboot
```

### Step 3: Run Script
```bash
scripts\load-seed-data.bat
```

### Expected Output
```
==========================================
Loading Seed Data for All Services
==========================================

Checking services...
[OK] ITSM Service is running
[OK] CMDB Service is running
[OK] ITOM Service is running
[OK] ITAM Service is running

Loading ITSM Incidents...
[OK] Created incident: INC-001
[OK] Created incident: INC-002
...

Loading CMDB Configuration Items...
[OK] Created CI: Production-DB-01
...

Loading ITOM Discovered Assets...
[OK] Created asset: Server-01
...

Loading ITAM Hardware Assets...
[OK] Created hardware: HW-001
...

==========================================
Seed Data Loaded Successfully!
==========================================

Summary:
  - ITSM Incidents: 5
  - CMDB Items: 8
  - ITOM Assets: 8
  - ITAM Hardware: 10
  - Total Records: 31

You can now login with:
  Email: admin@example.com
  Password: Admin@123456
```

---

## Method 2: Linux/Mac Bash Script

### Step 1: Open Terminal
```bash
# Navigate to project
cd /path/to/Snowrepo-springboot
```

### Step 2: Make Script Executable
```bash
chmod +x scripts/load-seed-data.sh
```

### Step 3: Run Script
```bash
./scripts/load-seed-data.sh
```

### Expected Output
Same as Windows batch script above

---

## Method 3: Python Script (Recommended)

### Step 1: Install Dependencies
```bash
pip install requests
```

### Step 2: Run Script

**Windows:**
```bash
python scripts/load_seed_data.py
```

**Linux/Mac:**
```bash
python3 scripts/load_seed_data.py
```

### Expected Output
```
==================================================
Loading Seed Data for All Services
==================================================

Checking services...
✓ ITSM is running
✓ CMDB is running
✓ ITOM is running
✓ ITAM is running

→ Loading ITSM Incidents...
✓ Created incident: INC-001
✓ Created incident: INC-002
✓ Created incident: INC-003
✓ Created incident: INC-004
✓ Created incident: INC-005

→ Loading CMDB Configuration Items...
✓ Created CI: Production-DB-01
✓ Created CI: Web-Server-01
✓ Created CI: Web-Server-02
✓ Created CI: Email-Server
✓ Created CI: Backup-Storage
✓ Created CI: Network-Switch-01
✓ Created CI: Firewall-01
✓ Created CI: VPN-Gateway

→ Loading ITOM Discovered Assets...
✓ Created asset: Server-01
✓ Created asset: Server-02
✓ Created asset: Server-03
✓ Created asset: Workstation-01
✓ Created asset: Printer-01
✓ Created asset: Switch-01
✓ Created asset: Firewall-01
✓ Created asset: Router-01

→ Loading ITAM Hardware Assets...
✓ Created hardware: HW-001
✓ Created hardware: HW-002
✓ Created hardware: HW-003
✓ Created hardware: HW-004
✓ Created hardware: HW-005
✓ Created hardware: HW-006
✓ Created hardware: HW-007
✓ Created hardware: HW-008
✓ Created hardware: HW-009
✓ Created hardware: HW-010

==================================================
✓ Seed Data Loaded Successfully!
==================================================

Summary:
  - ITSM Incidents: 5
  - CMDB Items: 8
  - ITOM Assets: 8
  - ITAM Hardware: 10
  - Total Records: 31

You can now login with:
  Email: admin@example.com
  Password: Admin@123456
```

---

## Troubleshooting

### Error: Service not running
```
[ERROR] ITSM Service is not running on http://localhost:8001
```

**Solution**: Start the ITSM service
```bash
cd itsm
java -jar target/itsm-1.0.0-SNAPSHOT.jar
```

### Error: curl not found (Windows)
```
'curl' is not recognized as an internal or external command
```

**Solution**: 
- Update Windows 10/11 (curl included in newer versions)
- Or install curl from: https://curl.se/download.html

### Error: Python requests not installed
```
ModuleNotFoundError: No module named 'requests'
```

**Solution**:
```bash
pip install requests
```

### Error: Connection refused
```
ConnectionError: Failed to establish a new connection
```

**Solution**: Ensure all services are running and accessible:
```bash
# Test ITSM service
curl http://localhost:8001/actuator/health

# Test CMDB service
curl http://localhost:8000/actuator/health

# Test ITOM service
curl http://localhost:8002/actuator/health

# Test ITAM service
curl http://localhost:8003/actuator/health
```

---

## Verify Seed Data Loaded

### Using Frontend
1. Open http://localhost:5173
2. Login with: `admin@example.com` / `Admin@123456`
3. Navigate to each service:
   - `/incidents` - Should show 5 incidents
   - `/cmdb` - Should show 8 configuration items
   - `/itom` - Should show 8 discovered assets
   - `/itam` - Should show 10 hardware assets

### Using API Endpoints

**Get all incidents:**
```bash
curl http://localhost:8001/api/v1/incidents
```

**Get all CIs:**
```bash
curl http://localhost:8000/api/v1/cis
```

**Get all ITOM assets:**
```bash
curl http://localhost:8002/api/v1/assets
```

**Get all ITAM hardware:**
```bash
curl http://localhost:8003/api/v1/assets
```

### Using Database

**PostgreSQL - ITSM:**
```bash
psql -h localhost -U postgres -d itsm_db -c "SELECT COUNT(*) FROM incidents;"
# Expected: 5
```

**PostgreSQL - CMDB:**
```bash
psql -h localhost -U postgres -d cmdb_db -c "SELECT COUNT(*) FROM configuration_items;"
# Expected: 8
```

**PostgreSQL - ITOM:**
```bash
psql -h localhost -U postgres -d itom_db -c "SELECT COUNT(*) FROM discovered_assets;"
# Expected: 8
```

**PostgreSQL - ITAM:**
```bash
psql -h localhost -U postgres -d itam_db -c "SELECT COUNT(*) FROM hardware_assets;"
# Expected: 10
```

---

## Next Steps After Loading Seed Data

1. ✅ Run seed data loader script
2. ✅ Verify data loaded in frontend/API
3. ⏳ Login with test users
4. ⏳ Test CRUD operations
5. ⏳ Test search and filtering
6. ⏳ Test dashboard analytics
7. ⏳ Run performance tests
8. ⏳ Deploy to production

---

## Test Users for Login

After loading seed data, use these credentials:

### Admin
- Email: `admin@example.com`
- Password: `Admin@123456`

### ITSM Manager
- Email: `itsm.manager@example.com`
- Password: `ITSM@123456`

### CMDB Admin
- Email: `cmdb.admin@example.com`
- Password: `CMDB@123456`

### Operations Manager
- Email: `ops.manager@example.com`
- Password: `Ops@123456`

---

## Summary

**Seed Data Loader Scripts**: ✅ READY

- **Windows**: `scripts/load-seed-data.bat`
- **Linux/Mac**: `scripts/load-seed-data.sh`
- **Python**: `scripts/load_seed_data.py`

**Seed Data**: 31 records
- ITSM: 5 incidents
- CMDB: 8 configuration items
- ITOM: 8 discovered assets
- ITAM: 10 hardware assets

**Test Users**: 4 accounts with different roles

---

**Status**: ✅ SEED DATA LOADER READY  
**Last Updated**: July 12, 2026
