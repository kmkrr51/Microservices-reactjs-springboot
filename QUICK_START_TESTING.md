# Quick Start Testing Guide

**Status**: ✅ READY  
**Date**: July 12, 2026

---

## 1. Start All Services

### Terminal 1: CMDB Service
```bash
cd cmdb
java -jar target/cmdb-1.0.0-SNAPSHOT.jar
# Runs on http://localhost:8000
```

### Terminal 2: ITSM Service
```bash
cd itsm
java -jar target/itsm-1.0.0-SNAPSHOT.jar
# Runs on http://localhost:8001
```

### Terminal 3: ITOM Service
```bash
cd itom
java -jar target/itom-1.0.0-SNAPSHOT.jar
# Runs on http://localhost:8002
```

### Terminal 4: ITAM Service
```bash
cd itam
java -jar target/itam-1.0.0-SNAPSHOT.jar
# Runs on http://localhost:8003
```

### Terminal 5: Frontend
```bash
cd frontend
npm run dev
# Runs on http://localhost:5173
```

---

## 2. Test Users (Copy & Paste)

### User 1: Admin
```
Email: admin@example.com
Password: Admin@123456
```

### User 2: ITSM Manager
```
Email: itsm.manager@example.com
Password: ITSM@123456
```

### User 3: CMDB Admin
```
Email: cmdb.admin@example.com
Password: CMDB@123456
```

### User 4: Operations Manager
```
Email: ops.manager@example.com
Password: Ops@123456
```

---

## 3. Quick Test Scenarios

### Test ITSM Service (5 min)
1. Login with: `admin@example.com` / `Admin@123456`
2. Navigate to `/incidents`
3. **Expected**: See 5 incidents (INC-001 to INC-005)
4. Click "Add Incident" and create a new one
5. Update incident status to "RESOLVED"
6. Assign incident to `itsm.manager@example.com`

### Test CMDB Service (5 min)
1. Navigate to `/cmdb`
2. **Expected**: See 8 configuration items
3. Click "Add Configuration Item"
4. Create new CI:
   - Name: Test-Server
   - Type: Server
   - Description: Test CI
   - Owner: admin@example.com
5. Change status to "RETIRED"

### Test ITOM Service (5 min)
1. Navigate to `/itom`
2. **Expected**: See 8 discovered assets
3. Click "Add Asset"
4. Create new asset:
   - Name: Test-Asset-01
   - Type: Server
   - IP: 192.168.1.200
   - Hostname: test-asset-01
5. Change status to "MONITORED"

### Test ITAM Service (5 min)
1. Navigate to `/itam`
2. **Expected**: See 10 hardware assets
3. Click "Add Hardware Asset"
4. Create new asset:
   - Tag: HW-TEST-001
   - Name: Test Laptop
   - Type: Laptop
   - Manufacturer: Dell
   - Model: XPS 13
   - Serial: TEST-001
5. Assign to `admin@example.com`

### Test Dashboard (3 min)
1. Navigate to `/dashboard`
2. **Expected**: See metrics:
   - Total Incidents: 5+
   - Total CIs: 8+
   - Total Assets: 8+
   - Total Hardware: 10+
3. View recent incidents widget
4. Check system status

---

## 4. API Testing with cURL

### Get All Incidents
```bash
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8001/api/v1/incidents
```

### Create Incident
```bash
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "incidentNumber": "INC-TEST-001",
    "title": "Test Incident",
    "description": "Test",
    "priority": "CRITICAL",
    "reporter": "test@example.com"
  }' \
  http://localhost:8001/api/v1/incidents
```

### Get All CIs
```bash
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8000/api/v1/cis
```

### Get All Assets (ITOM)
```bash
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8002/api/v1/assets
```

### Get All Hardware (ITAM)
```bash
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8003/api/v1/assets
```

---

## 5. Database Verification

### Check PostgreSQL
```bash
# Connect to ITSM database
psql -h localhost -U postgres -d itsm_db

# List incidents
SELECT * FROM incidents;

# Count records
SELECT COUNT(*) FROM incidents;
```

### Check MongoDB
```bash
# Connect to MongoDB
mongo mongodb://localhost:27017/itom_db

# List collections
show collections

# Count assets
db.discovered_assets.count()
```

---

## 6. Service Health Checks

### CMDB Health
```bash
curl http://localhost:8000/actuator/health
```

### ITSM Health
```bash
curl http://localhost:8001/actuator/health
```

### ITOM Health
```bash
curl http://localhost:8002/actuator/health
```

### ITAM Health
```bash
curl http://localhost:8003/actuator/health
```

---

## 7. Troubleshooting

### Service won't start
```bash
# Check if port is in use
lsof -i :8001

# Kill process using port
kill -9 <PID>
```

### Database connection error
```bash
# Test PostgreSQL
psql -h localhost -U postgres -d itsm_db -c "SELECT 1"

# Test MongoDB
mongo mongodb://localhost:27017/test --eval "db.adminCommand('ping')"
```

### Frontend can't connect to backend
```bash
# Check if backend is running
curl http://localhost:8001/actuator/health

# Check CORS configuration
# Verify service URLs in frontend/src/services/api.ts
```

---

## 8. Test Checklist

### Backend Services
- [ ] CMDB service running on port 8000
- [ ] ITSM service running on port 8001
- [ ] ITOM service running on port 8002
- [ ] ITAM service running on port 8003
- [ ] All health checks returning 200

### Frontend
- [ ] Frontend running on port 5173
- [ ] Login page displays test credentials
- [ ] Can login with admin@example.com
- [ ] Dashboard loads with metrics

### ITSM Service
- [ ] List incidents shows 5+ records
- [ ] Can create new incident
- [ ] Can update incident status
- [ ] Can assign incident

### CMDB Service
- [ ] List CIs shows 8+ records
- [ ] Can create new CI
- [ ] Can change CI status
- [ ] Can delete CI

### ITOM Service
- [ ] List assets shows 8+ records
- [ ] Can create new asset
- [ ] Can update asset status
- [ ] Can record discovery

### ITAM Service
- [ ] List hardware shows 10+ records
- [ ] Can create new hardware
- [ ] Can assign hardware
- [ ] Can change status

### Dashboard
- [ ] Metrics load from backend
- [ ] Recent incidents display
- [ ] System status shows
- [ ] All widgets render

---

## 9. Performance Baseline

### Expected Response Times
- GET /api/v1/incidents: < 200ms
- POST /api/v1/incidents: < 500ms
- GET /api/v1/cis: < 200ms
- GET /api/v1/assets: < 200ms

### Expected Load Times
- Frontend: < 3s
- Dashboard: < 2s
- Incident list: < 1s
- CMDB list: < 1s

---

## 10. Next Steps After Testing

1. ✅ Verify all services running
2. ✅ Test all CRUD operations
3. ✅ Check dashboard metrics
4. ✅ Verify authentication
5. ⏳ Run performance tests
6. ⏳ Execute security tests
7. ⏳ Load test with multiple users
8. ⏳ Deploy to production

---

**Status**: ✅ READY FOR TESTING  
**Estimated Time**: 30 minutes for full test cycle  
**Last Updated**: July 12, 2026
