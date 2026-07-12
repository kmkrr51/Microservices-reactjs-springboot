# CMDB (Configuration Management Database) - Detailed Specification

**Document Version**: 1.0  
**Date**: July 12, 2026  
**Status**: Planning Phase  
**Component Type**: Shared Infrastructure Service

---

## Overview

CMDB is a **shared, cross-cutting infrastructure component** that serves as the single source of truth for all configuration items (CIs) and their relationships across all 12 microservices. Unlike the 12 independent microservices, CMDB is a centralized platform service that every service depends on.

---

## CMDB Role & Responsibilities

### Primary Responsibilities

1. **Central CI Repository**
   - Single source of truth for all configuration items
   - Maintains complete CI inventory
   - Tracks CI attributes and metadata
   - Supports CI lifecycle management

2. **Relationship Engine**
   - Maintains dependencies and relationships between CIs
   - Tracks service dependencies
   - Manages impact relationships
   - Supports complex relationship queries

3. **Impact Analysis Engine**
   - Calculates impact of CI changes
   - Identifies affected services and CIs
   - Provides impact assessment for change management
   - Caches impact analysis results for performance

4. **Audit Trail**
   - Maintains complete history of CI changes
   - Tracks who changed what and when
   - Supports compliance and audit requirements
   - Event sourcing for CI changes

5. **Search & Discovery**
   - Full-text search capabilities for CIs
   - Advanced filtering and faceted search
   - CI discovery by type, status, owner
   - Real-time search index updates

---

## CMDB Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────┐
│              CMDB Shared Infrastructure                 │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  ┌──────────────────────────────────────────────────┐  │
│  │         CMDB API Gateway                         │  │
│  │  - REST API endpoints (/api/v1/cmdb/...)        │  │
│  │  - GraphQL interface (optional)                  │  │
│  │  - Authentication & authorization                │  │
│  │  - Request validation & transformation           │  │
│  └──────────────────────────────────────────────────┘  │
│                        ↓                                │
│  ┌──────────────────────────────────────────────────┐  │
│  │      CMDB Service Layer                          │  │
│  │  - CI Management Service                         │  │
│  │  - Relationship Service                          │  │
│  │  - Impact Analysis Service                       │  │
│  │  - Search Service                                │  │
│  │  - Audit Service                                 │  │
│  │  - Event Publishing Service                      │  │
│  └──────────────────────────────────────────────────┘  │
│                        ↓                                │
│  ┌──────────────────────────────────────────────────┐  │
│  │      CMDB Data Layer                             │  │
│  │  - PostgreSQL (CI data, relationships)           │  │
│  │  - Elasticsearch (CI search index)               │  │
│  │  - MongoDB (CI change history, audit logs)       │  │
│  │  - Redis (CI cache, relationship cache)          │  │
│  └──────────────────────────────────────────────────┘  │
│                        ↓                                │
│  ┌──────────────────────────────────────────────────┐  │
│  │      Event Bus Integration                       │  │
│  │  - Publishes CI events to Kafka                  │  │
│  │  - Consumes CI change events from services       │  │
│  │  - Maintains eventual consistency                │  │
│  │  - Dead letter queue for failed events           │  │
│  └──────────────────────────────────────────────────┘  │
│                                                         │
└─────────────────────────────────────────────────────────┘
                           ↑
        ┌──────────────────┼──────────────────┐
        │                  │                  │
    ┌───▼───┐          ┌───▼───┐         ┌───▼───┐
    │ ITSM  │          │ ITOM  │         │ ITAM  │
    │Service│          │Service│         │Service│
    └───────┘          └───────┘         └───────┘
        │                  │                  │
        └──────────────────┼──────────────────┘
           (All 12 services consume CMDB data)
```

---

## Configuration Item (CI) Types

### Infrastructure CIs

- **Server** - Physical/virtual servers, compute instances
- **Database** - Database instances, clusters
- **Application** - Software applications, services
- **NetworkDevice** - Routers, switches, firewalls, load balancers
- **StorageDevice** - Storage arrays, SANs, NAS
- **CloudResource** - Cloud instances, containers, serverless functions

### Business CIs

- **Service** - IT services, service offerings
- **BusinessService** - Business services, business processes
- **Department** - Organizational units, teams
- **Location** - Physical locations, data centers
- **Vendor** - Third-party vendors, service providers
- **Contract** - Service contracts, SLAs

### Relationship Types

- **Dependency** - Service depends on another service
- **Runs On** - Application runs on server
- **Connects To** - Network connections between devices
- **Supports** - Service supports business function
- **Owns** - Ownership relationships
- **Contains** - Container relationships (e.g., server contains application)
- **Hosts** - Hosting relationships
- **Uses** - Usage relationships

---

## CMDB Database Schema

### PostgreSQL Tables

```sql
-- Configuration Items (Main CI table)
CREATE TABLE configuration_items (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  ci_type VARCHAR(100) NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
  owner_id UUID,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  INDEX idx_ci_type (ci_type),
  INDEX idx_status (status),
  INDEX idx_owner (owner_id),
  INDEX idx_created_at (created_at)
);

-- CI Relationships (Relationships between CIs)
CREATE TABLE ci_relationships (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  source_ci_id UUID NOT NULL REFERENCES configuration_items(id) ON DELETE CASCADE,
  target_ci_id UUID NOT NULL REFERENCES configuration_items(id) ON DELETE CASCADE,
  relationship_type VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  INDEX idx_source (source_ci_id),
  INDEX idx_target (target_ci_id),
  INDEX idx_type (relationship_type),
  UNIQUE (source_ci_id, target_ci_id, relationship_type)
);

-- CI Attributes (Key-Value pairs for flexible attributes)
CREATE TABLE ci_attributes (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  ci_id UUID NOT NULL REFERENCES configuration_items(id) ON DELETE CASCADE,
  attribute_name VARCHAR(255) NOT NULL,
  attribute_value TEXT,
  attribute_type VARCHAR(50),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_ci (ci_id),
  INDEX idx_name (attribute_name),
  UNIQUE (ci_id, attribute_name)
);

-- CI Change History (Audit trail)
CREATE TABLE ci_change_history (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  ci_id UUID NOT NULL REFERENCES configuration_items(id) ON DELETE CASCADE,
  change_type VARCHAR(50) NOT NULL,
  old_value TEXT,
  new_value TEXT,
  changed_by UUID NOT NULL,
  changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  change_reason TEXT,
  INDEX idx_ci (ci_id),
  INDEX idx_changed_at (changed_at),
  INDEX idx_change_type (change_type)
);

-- CI Impact Analysis Cache (Performance optimization)
CREATE TABLE ci_impact_cache (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  source_ci_id UUID NOT NULL REFERENCES configuration_items(id) ON DELETE CASCADE,
  impacted_ci_ids UUID[] NOT NULL,
  impact_level VARCHAR(50),
  calculated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  expires_at TIMESTAMP NOT NULL,
  INDEX idx_source (source_ci_id),
  INDEX idx_expires (expires_at)
);
```

### MongoDB Collections

```javascript
// CI Change Events (Event Sourcing)
db.createCollection("ci_events", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["ci_id", "event_type", "timestamp", "actor_id"],
      properties: {
        _id: { bsonType: "objectId" },
        ci_id: { bsonType: "string" },
        event_type: { 
          enum: ["CI_CREATED", "CI_UPDATED", "CI_DELETED", "RELATIONSHIP_CREATED", "RELATIONSHIP_DELETED"]
        },
        timestamp: { bsonType: "date" },
        actor_id: { bsonType: "string" },
        changes: { bsonType: "object" },
        metadata: { bsonType: "object" }
      }
    }
  }
});

// CI Search Index (Denormalized for search)
db.createCollection("ci_search_index", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["ci_id", "ci_type", "name"],
      properties: {
        _id: { bsonType: "objectId" },
        ci_id: { bsonType: "string" },
        ci_type: { bsonType: "string" },
        name: { bsonType: "string" },
        description: { bsonType: "string" },
        attributes: { bsonType: "object" },
        related_cis: { bsonType: "array" },
        searchable_text: { bsonType: "string" },
        last_indexed: { bsonType: "date" }
      }
    }
  }
});

// Create indexes for performance
db.ci_events.createIndex({ ci_id: 1, timestamp: -1 });
db.ci_events.createIndex({ event_type: 1, timestamp: -1 });
db.ci_search_index.createIndex({ ci_id: 1 });
db.ci_search_index.createIndex({ ci_type: 1 });
db.ci_search_index.createIndex({ searchable_text: "text" });
```

---

## CMDB API Endpoints

### CI Management

```
POST   /api/v1/cmdb/cis
  Create a new CI
  Request: { ci_type, name, description, status, owner_id, attributes }
  Response: { id, ci_type, name, ... }

GET    /api/v1/cmdb/cis
  List CIs (paginated, filterable)
  Query: ?page=0&size=20&ci_type=Server&status=ACTIVE&sort=name,asc
  Response: { data: [...], meta: { total, page, size } }

GET    /api/v1/cmdb/cis/{id}
  Get CI details with relationships
  Response: { id, ci_type, name, attributes, relationships: [...] }

PATCH  /api/v1/cmdb/cis/{id}
  Update CI
  Request: { name, description, status, attributes }
  Response: { id, ci_type, name, ... }

DELETE /api/v1/cmdb/cis/{id}
  Soft delete CI
  Response: { success: true }

GET    /api/v1/cmdb/cis/{id}/history
  Get CI change history
  Response: { data: [...], meta: { total } }
```

### Relationships

```
POST   /api/v1/cmdb/relationships
  Create relationship between CIs
  Request: { source_ci_id, target_ci_id, relationship_type }
  Response: { id, source_ci_id, target_ci_id, relationship_type }

GET    /api/v1/cmdb/relationships
  List relationships
  Query: ?source_ci_id=...&relationship_type=...
  Response: { data: [...], meta: { total } }

GET    /api/v1/cmdb/cis/{id}/relationships
  Get all relationships for a CI
  Response: { outgoing: [...], incoming: [...] }

DELETE /api/v1/cmdb/relationships/{id}
  Delete relationship
  Response: { success: true }
```

### Impact Analysis

```
GET    /api/v1/cmdb/cis/{id}/impact
  Get impact of CI change
  Response: { source_ci: {...}, impacted_cis: [...], impact_level: "HIGH" }

GET    /api/v1/cmdb/cis/{id}/dependencies
  Get CI dependencies (what this CI depends on)
  Response: { data: [...], meta: { total } }

GET    /api/v1/cmdb/cis/{id}/dependents
  Get CIs that depend on this CI
  Response: { data: [...], meta: { total } }
```

### Search & Discovery

```
GET    /api/v1/cmdb/search?query=...
  Full-text search CIs
  Query: ?query=database&ci_type=Database
  Response: { data: [...], meta: { total } }

GET    /api/v1/cmdb/cis/type/{type}
  Get CIs by type
  Response: { data: [...], meta: { total } }

GET    /api/v1/cmdb/cis/status/{status}
  Get CIs by status
  Response: { data: [...], meta: { total } }

GET    /api/v1/cmdb/cis/owner/{owner_id}
  Get CIs owned by user
  Response: { data: [...], meta: { total } }
```

### Audit & Compliance

```
GET    /api/v1/cmdb/audit-log
  Get audit log entries
  Query: ?ci_id=...&change_type=...&from=...&to=...
  Response: { data: [...], meta: { total } }

GET    /api/v1/cmdb/cis/{id}/audit-log
  Get CI audit log
  Response: { data: [...], meta: { total } }
```

---

## CMDB Events

### Events Published by CMDB

- **CICreatedEvent** - New CI created
  - Payload: { ci_id, ci_type, name, created_by, created_at }
  
- **CIUpdatedEvent** - CI updated
  - Payload: { ci_id, changes: { field: { old, new } }, updated_by, updated_at }
  
- **CIDeletedEvent** - CI deleted (soft delete)
  - Payload: { ci_id, deleted_by, deleted_at }
  
- **RelationshipCreatedEvent** - Relationship created
  - Payload: { relationship_id, source_ci_id, target_ci_id, relationship_type, created_by }
  
- **RelationshipDeletedEvent** - Relationship deleted
  - Payload: { relationship_id, source_ci_id, target_ci_id, relationship_type, deleted_by }
  
- **CIStatusChangedEvent** - CI status changed
  - Payload: { ci_id, old_status, new_status, changed_by, changed_at }

### Events Consumed by CMDB

- **IncidentCreatedEvent** (from ITSM)
  - Action: Create/update CI relationships for affected systems
  
- **ChangeImplementedEvent** (from ITSM)
  - Action: Update CI status based on change implementation
  
- **AssetAcquiredEvent** (from ITAM)
  - Action: Create hardware/software CI
  
- **AssetRetiredEvent** (from ITAM)
  - Action: Update asset CI status to RETIRED
  
- **DiscoveryCompletedEvent** (from ITOM)
  - Action: Bulk CI creation/update from discovery results
  
- **ServiceMapUpdatedEvent** (from ITOM)
  - Action: Update service dependency relationships

---

## CMDB Shared Library

### Spring Boot CMDB Client Library

**Artifact**: `cmdb-client-spring-boot-starter`

**Features**:
- Auto-configured CMDB client beans
- Annotation-based CI queries
- Caching support
- Event publishing support
- Error handling and retries

**Usage Example**:

```java
@Configuration
@EnableCMDBClient
public class CMDBClientConfiguration {
  // Auto-configured CMDB client beans
}

@Service
public class IncidentService {
  
  @Autowired
  private CMDBClient cmdbClient;
  
  public void createIncident(CreateIncidentRequest request) {
    // Query CMDB for affected CIs
    List<ConfigurationItem> affectedCIs = 
      cmdbClient.searchCIs("name:" + request.getAffectedSystem());
    
    // Get impact analysis
    ImpactAnalysis impact = 
      cmdbClient.getImpactAnalysis(affectedCIs.get(0).getId());
    
    // Create incident with CI relationships
    Incident incident = new Incident(request);
    incident.setAffectedCIs(affectedCIs);
    incident.setImpactLevel(impact.getImpactLevel());
    incidentRepository.save(incident);
    
    // Publish event
    eventPublisher.publishEvent(new IncidentCreatedEvent(incident));
  }
}
```

---

## CMDB Performance & Optimization

### Caching Strategy

- **Redis Cache for CIs**
  - Cache frequently accessed CIs
  - TTL: 1 hour default (configurable)
  - Cache invalidation on CI updates
  
- **Relationship Cache**
  - Cache CI relationships
  - TTL: 30 minutes
  - Invalidate on relationship changes
  
- **Impact Analysis Cache**
  - Cache impact analysis results
  - TTL: 1 hour
  - Invalidate on CI or relationship changes

### Search Optimization

- **Elasticsearch Integration**
  - Full-text CI search
  - Denormalized search index
  - Faceted search support
  - Real-time index updates via Kafka
  
- **Search Index**
  - Index CI name, description, attributes
  - Support for wildcard and fuzzy search
  - Facets: ci_type, status, owner

### Query Optimization

- **Database Indexes**
  - Index on CI type, status, owner
  - Index on relationship source/target
  - Index on created_at, updated_at
  
- **Relationship Query Optimization**
  - Efficient graph traversal
  - Limit relationship depth
  - Cache frequently accessed paths
  
- **Pagination**
  - Cursor-based pagination for large result sets
  - Default page size: 20
  - Maximum page size: 100

---

## CMDB Implementation Timeline

### Phase 1 (Months 1-3): Foundation

**Deliverables**:
- CMDB database schema design and creation
- PostgreSQL and MongoDB setup
- CMDB API development (CI CRUD operations)
- CMDB Spring Boot starter library
- Basic CI management endpoints
- Relationship management endpoints
- Authentication and authorization

**Team**: 2-3 engineers

### Phase 2 (Months 4-6): Core Features

**Deliverables**:
- Impact analysis engine implementation
- Search and discovery capabilities
- Event publishing/consumption setup
- Audit logging implementation
- Redis caching implementation
- Comprehensive API documentation
- Unit and integration tests (85%+ coverage)

**Team**: 2-3 engineers

### Phase 3 (Months 7-9): Advanced Features

**Deliverables**:
- Advanced relationship queries
- Performance optimization
- Elasticsearch integration
- Bulk CI operations
- CMDB UI (optional)
- Load testing and optimization
- Production readiness assessment

**Team**: 2-3 engineers

---

## CMDB Integration with Microservices

### ITSM Service Integration

- Query CMDB for affected CIs when incident created
- Update CMDB when change is implemented
- Link incidents to CIs for impact analysis
- Subscribe to CI change events

### ITOM Service Integration

- Create/update CIs during discovery
- Maintain service dependency relationships
- Update CI status based on monitoring events
- Publish discovery events to CMDB

### ITAM Service Integration

- Create hardware/software CIs
- Maintain license relationships
- Track asset lifecycle in CMDB
- Publish asset events to CMDB

### All Services Integration

- Query CMDB for CI data
- Publish events when CIs are affected
- Subscribe to CI change events
- Use CMDB for impact analysis
- Cache CI data locally for performance

---

## CMDB Governance & Maintenance

### Data Quality

- Regular CI audits
- Duplicate CI detection and merging
- Orphaned CI cleanup
- Relationship validation

### Access Control

- Role-based access to CMDB
- CI ownership and responsibility
- Audit trail for all changes
- Compliance with security policies

### Disaster Recovery

- Daily backups of CMDB data
- Point-in-time recovery capability
- Replication to secondary database
- RTO: < 15 minutes
- RPO: < 5 minutes

---

## Conclusion

CMDB is a critical shared infrastructure component that serves as the foundation for all 12 microservices. Its proper design and implementation are essential for:

- Maintaining a single source of truth for CIs
- Enabling impact analysis for changes
- Supporting compliance and audit requirements
- Providing search and discovery capabilities
- Facilitating service-to-service communication

The phased implementation approach ensures that CMDB is built incrementally with continuous value delivery, starting with basic CI management and evolving to advanced features like impact analysis and Elasticsearch integration.

---

**Document Classification**: Internal Use  
**Last Updated**: July 12, 2026  
**Next Review**: August 12, 2026
