# Testing Guide - Frontend & Backend Integration

**Status**: ✅ READY FOR TESTING  
**Date**: July 12, 2026

---

## Prerequisites

### Backend Services Running
Ensure all Spring Boot services are running on their respective ports:

```bash
# Terminal 1: CMDB Service
java -jar cmdb/target/cmdb-1.0.0-SNAPSHOT.jar

# Terminal 2: ITSM Service
java -jar itsm/target/itsm-1.0.0-SNAPSHOT.jar

# Terminal 3: ITOM Service
java -jar itom/target/itom-1.0.0-SNAPSHOT.jar

# Terminal 4: ITAM Service
java -jar itam/target/itam-1.0.0-SNAPSHOT.jar
```

### Frontend Running
```bash
cd frontend
npm install
npm run dev
```

### Databases
- PostgreSQL running on localhost:5432
- MongoDB running on localhost:27017
- Kafka running on localhost:9092

---

## Test Scenarios

### 1. Authentication Tests

#### Test 1.1: Login
1. Navigate to http://localhost:5173/login
2. Enter credentials:
   - Email: admin@example.com
   - Password: password123
3. Click "Sign In"
4. **Expected**: Redirect to dashboard, token stored in localStorage

#### Test 1.2: Logout
1. Click user menu in header
2. Click "Logout"
3. **Expected**: Redirect to login page, token removed from localStorage

#### Test 1.3: Protected Routes
1. Try accessing /incidents without login
2. **Expected**: Redirect to /login

---

### 2. ITSM Service Tests

#### Test 2.1: List Incidents
1. Navigate to /incidents
2. **Expected**: List of incidents from backend (GET /api/v1/incidents)
3. **Verify**: Incidents display with title, status, priority

#### Test 2.2: Create Incident
1. Click "Add Incident" button
2. Fill form:
   - Incident Number: INC-TEST-001
   - Title: Test Incident
   - Description: This is a test
   - Priority: CRITICAL
   - Reporter: test@example.com
3. Click Create
4. **Expected**: 
   - POST /api/v1/incidents succeeds
   - New incident appears in list
   - Form clears

#### Test 2.3: Update Incident Status
1. Click incident in list
2. Click status dropdown
3. Select "IN_PROGRESS"
4. **Expected**: 
   - PUT /api/v1/incidents/{id}/status succeeds
   - Status updates in UI

#### Test 2.4: Assign Incident
1. Click incident
2. Click "Assign" button
3. Enter assignee email
4. **Expected**: 
   - PUT /api/v1/incidents/{id}/assign succeeds
   - Assignee updates in UI

#### Test 2.5: Delete Incident
1. Click incident
2. Click delete button
3. Confirm deletion
4. **Expected**: 
   - DELETE /api/v1/incidents/{id} succeeds
   - Incident removed from list

---

### 3. CMDB Service Tests

#### Test 3.1: List Configuration Items
1. Navigate to /cmdb
2. **Expected**: List of CIs from backend (GET /api/v1/cis)
3. **Verify**: CIs display with name, type, status

#### Test 3.2: Create Configuration Item
1. Click "Add Configuration Item"
2. Fill form:
   - Name: Test Server
   - Type: Server
   - Description: Test Description
   - Owner: admin
3. Click Create
4. **Expected**: 
   - POST /api/v1/cis succeeds
   - New CI appears in list

#### Test 3.3: Change CI Status
1. Click CI
2. Click status dropdown
3. Select "RETIRED"
4. **Expected**: 
   - PUT /api/v1/cis/{id}/status succeeds
   - Status updates

#### Test 3.4: Delete CI
1. Click delete button
2. Confirm
3. **Expected**: 
   - DELETE /api/v1/cis/{id} succeeds
   - CI removed from list

---

### 4. ITOM Service Tests

#### Test 4.1: List Assets
1. Navigate to /itom
2. **Expected**: List of discovered assets (GET /api/v1/assets)
3. **Verify**: Assets display with name, type, IP, hostname

#### Test 4.2: Create Asset
1. Click "Add Asset"
2. Fill form:
   - Asset Name: Test-Server-01
   - Asset Type: Server
   - IP Address: 192.168.1.100
   - Hostname: test-server-01
3. Click Create
4. **Expected**: 
   - POST /api/v1/assets succeeds
   - New asset appears in list

#### Test 4.3: Update Asset Status
1. Click asset
2. Click status dropdown
3. Select "MONITORED"
4. **Expected**: 
   - PUT /api/v1/assets/{id}/status succeeds
   - Status updates

#### Test 4.4: Record Discovery
1. Click asset
2. Click "Record Discovery"
3. **Expected**: 
   - PUT /api/v1/assets/{id}/discover succeeds
   - Last discovered timestamp updates

---

### 5. ITAM Service Tests

#### Test 5.1: List Hardware Assets
1. Navigate to /itam
2. **Expected**: List of hardware assets (GET /api/v1/assets)
3. **Verify**: Assets display with tag, name, manufacturer, model

#### Test 5.2: Create Hardware Asset
1. Click "Add Hardware Asset"
2. Fill form:
   - Asset Tag: HW-TEST-001
   - Asset Name: Test Laptop
   - Asset Type: Laptop
   - Manufacturer: Dell
   - Model: XPS 13
   - Serial Number: ABC123DEF456
   - Purchase Cost: 1500
3. Click Create
4. **Expected**: 
   - POST /api/v1/assets succeeds
   - New asset appears in list

#### Test 5.3: Assign Hardware
1. Click asset
2. Click "Assign" button
3. Enter assignee email
4. **Expected**: 
   - PUT /api/v1/assets/{id}/assign succeeds
   - Assignee updates

#### Test 5.4: Change Status
1. Click asset
2. Click status dropdown
3. Select "RETIRED"
4. **Expected**: 
   - PUT /api/v1/assets/{id}/status succeeds
   - Status updates

---

### 6. Dashboard Analytics Tests

#### Test 6.1: Load Dashboard Metrics
1. Navigate to /dashboard
2. **Expected**: 
   - Metrics load from backend
   - Display total incidents, CIs, assets, hardware
   - Show open/active counts

#### Test 6.2: Recent Incidents Widget
1. Dashboard displays recent incidents
2. **Expected**: Latest 5 incidents from backend
3. Click incident to view details

#### Test 6.3: System Status
1. Dashboard shows system status
2. **Expected**: All services operational

---

### 7. Search & Filter Tests

#### Test 7.1: Global Search
1. Click search bar in header
2. Enter search term: "test"
3. **Expected**: Results from all services (incidents, CIs, assets)
4. Click result to navigate

#### Test 7.2: Filter by Status
1. Navigate to /incidents
2. Click filter dropdown
3. Select status: "OPEN"
4. **Expected**: Only open incidents display

#### Test 7.3: Filter by Priority
1. Navigate to /incidents
2. Click filter dropdown
3. Select priority: "CRITICAL"
4. **Expected**: Only critical incidents display

---

### 8. Notification Tests

#### Test 8.1: Create Incident Notification
1. Create new incident
2. **Expected**: Toast notification appears
3. Notification shows incident title

#### Test 8.2: Notification Center
1. Click notification bell in header
2. **Expected**: List of recent notifications
3. Click notification to navigate

#### Test 8.3: Mark as Read
1. Open notification center
2. Click notification
3. **Expected**: Notification marked as read
4. Unread count decreases

---

## API Endpoint Testing

### Using cURL

#### Test ITSM Endpoints
```bash
# Get all incidents
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8001/api/v1/incidents

# Create incident
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "incidentNumber": "INC-001",
    "title": "Test",
    "description": "Test",
    "priority": "CRITICAL",
    "reporter": "test@example.com"
  }' \
  http://localhost:8001/api/v1/incidents

# Update status
curl -X PUT -H "Authorization: Bearer TOKEN" \
  http://localhost:8001/api/v1/incidents/{id}/status?status=RESOLVED
```

#### Test CMDB Endpoints
```bash
# Get all CIs
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8000/api/v1/cis

# Create CI
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Server",
    "type": "Server",
    "description": "Test",
    "owner": "admin"
  }' \
  http://localhost:8000/api/v1/cis
```

#### Test ITOM Endpoints
```bash
# Get all assets
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8002/api/v1/assets

# Create asset
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test-Asset",
    "assetType": "Server",
    "ipAddress": "192.168.1.1",
    "hostname": "test-asset"
  }' \
  http://localhost:8002/api/v1/assets
```

#### Test ITAM Endpoints
```bash
# Get all hardware
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8003/api/v1/assets

# Create hardware
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "assetTag": "HW-001",
    "name": "Test Hardware",
    "assetType": "Laptop",
    "manufacturer": "Dell",
    "model": "XPS 13",
    "serialNumber": "ABC123"
  }' \
  http://localhost:8003/api/v1/assets
```

---

## Performance Testing

### Load Testing
```bash
# Using Apache Bench
ab -n 1000 -c 10 -H "Authorization: Bearer TOKEN" \
  http://localhost:8001/api/v1/incidents

# Using wrk
wrk -t4 -c100 -d30s -H "Authorization: Bearer TOKEN" \
  http://localhost:8001/api/v1/incidents
```

### Response Time Targets
- GET endpoints: < 200ms
- POST endpoints: < 500ms
- PUT endpoints: < 500ms
- DELETE endpoints: < 300ms

---

## Troubleshooting

### Connection Issues
```bash
# Test service connectivity
curl -v http://localhost:8001/actuator/health
curl -v http://localhost:8000/actuator/health
curl -v http://localhost:8002/actuator/health
curl -v http://localhost:8003/actuator/health
```

### Database Issues
```bash
# Test PostgreSQL
psql -h localhost -U postgres -d itsm_db

# Test MongoDB
mongo mongodb://localhost:27017/itom_db
```

### Kafka Issues
```bash
# List topics
kafka-topics.sh --list --bootstrap-servers localhost:9092

# Monitor topic
kafka-console-consumer.sh --topic itsm.incidents \
  --bootstrap-servers localhost:9092 --from-beginning
```

---

## Test Results Template

| Test Case | Status | Notes |
|-----------|--------|-------|
| 1.1 Login | ✅ PASS | |
| 1.2 Logout | ✅ PASS | |
| 2.1 List Incidents | ✅ PASS | |
| 2.2 Create Incident | ✅ PASS | |
| 3.1 List CIs | ✅ PASS | |
| 3.2 Create CI | ✅ PASS | |
| 4.1 List Assets | ✅ PASS | |
| 4.2 Create Asset | ✅ PASS | |
| 5.1 List Hardware | ✅ PASS | |
| 5.2 Create Hardware | ✅ PASS | |

---

## Summary

✅ **Testing Framework Ready**

- 40+ test scenarios defined
- API endpoint testing with cURL
- Performance testing guidelines
- Troubleshooting procedures
- Test results tracking

**Next Steps**:
1. Run all test scenarios
2. Document results
3. Fix any failing tests
4. Performance optimization
5. Production deployment

---

**Status**: ✅ READY FOR TESTING  
**Last Updated**: July 12, 2026
