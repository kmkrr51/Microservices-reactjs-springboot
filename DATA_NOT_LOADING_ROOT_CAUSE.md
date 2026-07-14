# Root Cause Analysis: Why Data is Not Populating in Frontend

## Executive Summary

**Data is NOT loading in the frontend because of a CRITICAL MISMATCH between what the frontend expects from the API and what the backend actually returns.**

---

## The Core Problem

### Frontend Expectation vs Backend Reality

**Frontend expects API response format**:
```typescript
// incidentThunks.ts (Line 9-12)
const response = await apiClient.get<{ incidents: Incident[] }>(
  "/incidents",
);
return response.data.incidents || [];
```

The frontend expects:
```json
{
  "incidents": [
    { "id": "...", "title": "...", ... },
    { "id": "...", "title": "...", ... }
  ]
}
```

**Backend actually returns**:
```typescript
// IncidentController.java (Line 67-75)
@GetMapping
public ResponseEntity<List<IncidentResponse>> getAllIncidents() {
  List<IncidentResponse> incidents = incidentService.getAllIncidents()
      .stream()
      .map(IncidentResponse::fromEntity)
      .toList();
  return ResponseEntity.ok(incidents);
}
```

The backend returns:
```json
[
  { "id": "...", "title": "...", ... },
  { "id": "...", "title": "...", ... }
]
```

---

## The Mismatch Explained

| Aspect | Frontend Expects | Backend Returns | Result |
|--------|------------------|-----------------|--------|
| Response Type | Object with `incidents` property | Array directly | ❌ MISMATCH |
| Path | `/incidents` | `/api/v1/incidents` | ❌ MISMATCH |
| Data Access | `response.data.incidents` | `response.data` is array | ❌ Returns undefined |
| Redux Store | Gets `undefined` | Should get array | ❌ No data in store |

---

## Detailed Issue Breakdown

### Issue 1: Wrong API Path

**Frontend Code** (`incidentThunks.ts:10`):
```typescript
const response = await apiClient.get<{ incidents: Incident[] }>(
  "/incidents",  // ❌ WRONG PATH
);
```

**What happens**:
- Frontend calls: `GET http://localhost:8016/incidents`
- Backend expects: `GET http://localhost:8016/api/v1/incidents`
- Result: **404 Not Found**

**Correct path should be**:
```typescript
const response = await apiClient.get<Incident[]>(
  "/api/v1/incidents",  // ✅ CORRECT
);
```

---

### Issue 2: Wrong Response Format Expectation

**Frontend Code** (`incidentThunks.ts:9-12`):
```typescript
const response = await apiClient.get<{ incidents: Incident[] }>(
  "/incidents",
);
return response.data.incidents || [];  // ❌ Expects nested object
```

**Backend Code** (`IncidentController.java:67-75`):
```java
@GetMapping
public ResponseEntity<List<IncidentResponse>> getAllIncidents() {
  // Returns: List<IncidentResponse>
  // Which serializes to: [{ ... }, { ... }]
  // NOT: { incidents: [{ ... }, { ... }] }
  return ResponseEntity.ok(incidents);
}
```

**What happens**:
1. Backend returns: `[{ id: "...", title: "..." }, { id: "...", title: "..." }]`
2. Frontend tries to access: `response.data.incidents`
3. Result: `undefined` (because array doesn't have `.incidents` property)
4. Redux gets: `undefined || [] = []`
5. Frontend displays: **Empty list**

**Correct expectation should be**:
```typescript
const response = await apiClient.get<Incident[]>(
  "/api/v1/incidents",
);
return response.data;  // ✅ Return array directly
```

---

### Issue 3: Same Problem in All Services

This issue affects ALL services, not just ITSM:

**CMDB Service** (`cmdbService.ts:20-23`):
```typescript
getCIs: async (): Promise<ConfigurationItem[]> => {
  const response = await cmdbClient.get<ConfigurationItem[]>("/api/v1/cis");
  return response.data;  // ✅ This one is CORRECT
}
```

**ITOM Service** (`itomService.ts:23-26`):
```typescript
getAssets: async (): Promise<DiscoveredAsset[]> => {
  const response = await itomClient.get<DiscoveredAsset[]>("/api/v1/assets");
  return response.data;  // ✅ This one is CORRECT
}
```

**ITAM Service** (`itamService.ts:26-29`):
```typescript
getAssets: async (): Promise<HardwareAsset[]> => {
  const response = await itamClient.get<HardwareAsset[]>("/api/v1/assets");
  return response.data;  // ✅ This one is CORRECT
}
```

**But the Thunks are WRONG**:

**ITSM Thunk** (`incidentThunks.ts:9-12`):
```typescript
const response = await apiClient.get<{ incidents: Incident[] }>(
  "/incidents",  // ❌ WRONG PATH
);
return response.data.incidents || [];  // ❌ WRONG FORMAT
```

---

## Data Flow Diagram (Current - Broken)

```
Frontend Component (IncidentPage)
    ↓
Calls: useDashboardData()
    ↓
Dispatches: fetchIncidents()
    ↓
Thunk calls: apiClient.get("/incidents")
    ↓
❌ FAILS: 404 Not Found (path is wrong)
    ↓
Even if it didn't fail:
    ↓
Backend returns: [{ id: "...", title: "..." }, ...]
    ↓
Frontend expects: { incidents: [{ id: "...", title: "..." }, ...] }
    ↓
Frontend accesses: response.data.incidents
    ↓
Gets: undefined (array doesn't have .incidents property)
    ↓
Redux receives: undefined || [] = []
    ↓
Frontend displays: EMPTY LIST
```

---

## Why Only Some Data Might Load

**If CMDB/ITOM/ITAM data loads**:
- Their services are correct: `response.data` returns array directly
- Their thunks are correct: They don't try to access `.incidents` property

**Why ITSM data doesn't load**:
- Path is wrong: `/incidents` instead of `/api/v1/incidents`
- Format expectation is wrong: Expects `{ incidents: [...] }` but gets `[...]`

---

## Verification

To verify this is the issue, check browser console:

1. **Open DevTools** → Network tab
2. **Look for requests to**:
   - `GET http://localhost:8016/incidents` → Should see **404 Not Found**
   - Should be: `GET http://localhost:8016/api/v1/incidents` → Should see **200 OK**

3. **Check Redux DevTools**:
   - `state.incidents.incidents` → Should be `[]` (empty)
   - Should be populated with incident data

---

## Summary of Issues

| Issue | Location | Problem | Impact |
|-------|----------|---------|--------|
| 1 | incidentThunks.ts:10 | Path is `/incidents` not `/api/v1/incidents` | 404 error |
| 2 | incidentThunks.ts:9-12 | Expects `{ incidents: [...] }` format | Gets undefined |
| 3 | incidentThunks.ts:12 | Tries to access `.incidents` property | Returns empty array |
| 4 | incidentSlice.ts:66 | Redux receives undefined/empty | No data in store |
| 5 | IncidentList.tsx:165 | Component renders empty table | No data displayed |

---

## Root Cause Summary

**The frontend's `fetchIncidents` thunk is calling the wrong endpoint with the wrong response format expectation.**

1. **Wrong endpoint**: `/incidents` should be `/api/v1/incidents`
2. **Wrong format**: Expects `{ incidents: [...] }` but backend returns `[...]`
3. **Wrong data extraction**: `response.data.incidents` returns `undefined`
4. **Result**: Redux store gets empty array, frontend displays nothing

---

## Conclusion

**Data is in the database and the backend is returning it correctly, but the frontend is not receiving it because**:

1. The API path is incorrect (404 error)
2. The response format expectation is incorrect (undefined error)
3. The data extraction logic is incorrect (empty array)

**This is a frontend-only issue. The backend is working correctly.**

**To fix**: Update the `fetchIncidents` thunk to use the correct path and response format.
