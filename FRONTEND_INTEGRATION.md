# Frontend Integration Guide

**Status**: ✅ COMPLETE  
**Date**: July 12, 2026

---

## Overview

React frontend has been successfully integrated with Spring Boot microservices backend. All Phase 2 services (CMDB, ITSM, ITOM, ITAM) are now connected to the frontend.

---

## Services Integrated

### 1. ITSM Service (Port 8001)
**Status**: ✅ INTEGRATED

**Frontend Integration**:
- Service: `incidentService` (updated)
- Page: `IncidentPage.tsx`
- Endpoints:
  - `GET /api/v1/incidents` - List all incidents
  - `GET /api/v1/incidents/{id}` - Get incident by ID
  - `GET /api/v1/incidents/number/{incidentNumber}` - Get by number
  - `POST /api/v1/incidents` - Create incident
  - `PUT /api/v1/incidents/{id}/status` - Update status
  - `PUT /api/v1/incidents/{id}/assign` - Assign incident
  - `DELETE /api/v1/incidents/{id}` - Delete incident

**Features**:
- List all incidents
- Create new incident
- View incident details
- Update incident status
- Assign incident to user
- Delete incident

---

### 2. CMDB Service (Port 8000)
**Status**: ✅ INTEGRATED

**Frontend Integration**:
- Service: `cmdbService` (new)
- Page: `CMDBPage.tsx` (new)
- Endpoints:
  - `GET /api/v1/cis` - List all CIs
  - `GET /api/v1/cis/{id}` - Get CI by ID
  - `GET /api/v1/cis/name/{name}` - Get by name
  - `POST /api/v1/cis` - Create CI
  - `PATCH /api/v1/cis/{id}` - Update CI
  - `PUT /api/v1/cis/{id}/status` - Change status
  - `DELETE /api/v1/cis/{id}` - Delete CI

**Features**:
- List all configuration items
- Create new CI
- View CI details
- Change CI status
- Delete CI

---

### 3. ITOM Service (Port 8002)
**Status**: ✅ INTEGRATED

**Frontend Integration**:
- Service: `itomService` (new)
- Page: `ITOMPage.tsx` (new)
- Endpoints:
  - `GET /api/v1/assets` - List all assets
  - `GET /api/v1/assets/{id}` - Get asset by ID
  - `GET /api/v1/assets/hostname/{hostname}` - Get by hostname
  - `GET /api/v1/assets/ip/{ipAddress}` - Get by IP
  - `POST /api/v1/assets` - Create asset
  - `PUT /api/v1/assets/{id}/status` - Change status
  - `PUT /api/v1/assets/{id}/discover` - Record discovery
  - `DELETE /api/v1/assets/{id}` - Delete asset

**Features**:
- List all discovered assets
- Create new asset
- View asset details
- Change asset status
- Record asset discovery
- Delete asset

---

### 4. ITAM Service (Port 8003)
**Status**: ✅ INTEGRATED

**Frontend Integration**:
- Service: `itamService` (new)
- Page: `ITAMPage.tsx` (new)
- Endpoints:
  - `GET /api/v1/assets` - List all assets
  - `GET /api/v1/assets/{id}` - Get asset by ID
  - `GET /api/v1/assets/tag/{assetTag}` - Get by tag
  - `GET /api/v1/assets/serial/{serialNumber}` - Get by serial
  - `POST /api/v1/assets` - Create asset
  - `PUT /api/v1/assets/{id}/status` - Change status
  - `PUT /api/v1/assets/{id}/assign` - Assign asset
  - `DELETE /api/v1/assets/{id}` - Delete asset

**Features**:
- List all hardware assets
- Create new asset
- View asset details
- Change asset status
- Assign asset to user
- Delete asset

---

## File Structure

```
frontend/src/
├── services/
│   ├── api.ts (updated)
│   ├── auth.service.ts
│   ├── incident.service.ts (updated)
│   ├── cmdb.service.ts (new)
│   ├── itom.service.ts (new)
│   └── itam.service.ts (new)
├── pages/
│   ├── IncidentPage.tsx (updated)
│   ├── CMDBPage.tsx (new)
│   ├── ITOMPage.tsx (new)
│   ├── ITAMPage.tsx (new)
│   ├── DashboardPage.tsx
│   ├── ProblemPage.tsx
│   ├── ChangePage.tsx
│   ├── RequestPage.tsx
│   ├── NotificationCenterPage.tsx
│   ├── AuditTrailPage.tsx
│   └── LoginPage.tsx
├── components/
│   └── layout/
│       └── Sidebar.tsx (updated)
└── App.tsx (updated)
```

---

## API Configuration

### Service URLs
```typescript
export const SERVICE_URLS = {
  CMDB: "http://localhost:8000",
  ITSM: "http://localhost:8001",
  ITOM: "http://localhost:8002",
  ITAM: "http://localhost:8003",
  API_GATEWAY: "http://localhost:8080",
};
```

### API Client
- Base URL: Service-specific
- Headers: Content-Type: application/json
- Auth: Bearer token in Authorization header
- Error Handling: Automatic 401 redirect to login

---

## Navigation

### Sidebar Menu
- Dashboard
- Incidents (ITSM)
- Problems (ITSM)
- Changes (ITSM)
- Requests (ITSM)
- **CMDB** (new)
- **ITOM** (new)
- **ITAM** (new)
- Notifications
- Audit Trail

---

## Running the Frontend

### Development
```bash
cd frontend
npm install
npm run dev
```

### Build
```bash
npm run build
```

### Test
```bash
npm test
```

---

## Testing Integration

### Test ITSM Service
1. Navigate to `/incidents`
2. Click "Add Incident"
3. Fill form with:
   - Incident Number: INC-001
   - Title: Test Incident
   - Description: Test Description
   - Priority: CRITICAL
   - Reporter: test@example.com
4. Click Create
5. Verify incident appears in list

### Test CMDB Service
1. Navigate to `/cmdb`
2. Click "Add Configuration Item"
3. Fill form with:
   - Name: Test Server
   - Type: Server
   - Description: Test Description
   - Owner: admin
4. Click Create
5. Verify CI appears in list

### Test ITOM Service
1. Navigate to `/itom`
2. Click "Add Asset"
3. Fill form with:
   - Asset Name: Test Asset
   - Asset Type: Server
   - IP Address: 192.168.1.1
   - Hostname: test-server
4. Click Create
5. Verify asset appears in list

### Test ITAM Service
1. Navigate to `/itam`
2. Click "Add Hardware Asset"
3. Fill form with:
   - Asset Tag: HW-001
   - Asset Name: Test Hardware
   - Asset Type: Laptop
   - Manufacturer: Dell
   - Model: XPS 13
   - Serial Number: ABC123
4. Click Create
5. Verify asset appears in list

---

## Error Handling

### Common Issues

**Connection Refused**
- Ensure Spring Boot services are running
- Check service ports (8000-8003)
- Verify firewall settings

**401 Unauthorized**
- Check authentication token
- Verify token in localStorage
- Login again if needed

**CORS Issues**
- Configure CORS in Spring Boot services
- Add frontend URL to allowed origins

**API Not Found**
- Verify endpoint paths match Spring Boot routes
- Check service URLs in api.ts
- Review API documentation

---

## Future Enhancements

### Planned Features
1. **Problem Management** - Problem domain model and API
2. **Change Management** - Change domain model and API
3. **Service Requests** - Request domain model and API
4. **Advanced Filtering** - Search and filter capabilities
5. **Bulk Operations** - Batch create/update/delete
6. **Export/Import** - CSV export and import
7. **Dashboard Widgets** - Real-time metrics and charts
8. **Notifications** - Real-time event notifications
9. **Audit Trail** - Activity logging and tracking
10. **Advanced Reporting** - Custom reports and analytics

---

## Code Examples

### Using ITSM Service
```typescript
import { incidentService } from "@/services/incident.service";

// Get all incidents
const incidents = await incidentService.getIncidents();

// Create incident
const newIncident = await incidentService.createIncident({
  incidentNumber: "INC-001",
  title: "System Down",
  description: "Critical outage",
  priority: "CRITICAL",
  reporter: "user@example.com",
});

// Update status
const updated = await incidentService.updateIncidentStatus(
  incidentId,
  "RESOLVED"
);

// Assign incident
const assigned = await incidentService.assignIncident(
  incidentId,
  "support@example.com"
);
```

### Using CMDB Service
```typescript
import { cmdbService } from "@/services/cmdb.service";

// Get all CIs
const items = await cmdbService.getCIs();

// Create CI
const newCI = await cmdbService.createCI({
  name: "Production Server",
  type: "Server",
  description: "Main production server",
  owner: "admin",
});

// Change status
const updated = await cmdbService.changeCIStatus(
  ciId,
  "RETIRED"
);
```

---

## Summary

✅ **Frontend Integration Complete**

- 4 new services created (CMDB, ITOM, ITAM, updated ITSM)
- 4 new pages created (CMDB, ITOM, ITAM, updated Incident)
- Navigation updated with new menu items
- API client configured for all services
- Error handling implemented
- Ready for testing and deployment

---

**Status**: ✅ COMPLETE  
**Last Updated**: July 12, 2026
