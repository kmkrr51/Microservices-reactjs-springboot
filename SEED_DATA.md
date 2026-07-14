# Seed Data & Test Users

**Status**: ✅ READY  
**Date**: July 12, 2026

---

## Test Users

### User 1: Admin
- **Email**: admin@example.com
- **Password**: Admin@123456
- **Role**: Administrator
- **Permissions**: Full access to all services

### User 2: ITSM Manager
- **Email**: itsm.manager@example.com
- **Password**: ITSM@123456
- **Role**: ITSM Manager
- **Permissions**: Incident, Problem, Change, Request management

### User 3: CMDB Administrator
- **Email**: cmdb.admin@example.com
- **Password**: CMDB@123456
- **Role**: CMDB Administrator
- **Permissions**: Configuration item management

### User 4: Operations Manager
- **Email**: ops.manager@example.com
- **Password**: Ops@123456
- **Role**: Operations Manager
- **Permissions**: Asset and ITOM management

---

## ITSM Service Seed Data

### Incidents
```json
[
  {
    "incidentNumber": "INC-001",
    "title": "Production Database Down",
    "description": "Critical production database is not responding",
    "priority": "CRITICAL",
    "status": "IN_PROGRESS",
    "reporter": "admin@example.com",
    "assignedTo": "itsm.manager@example.com"
  },
  {
    "incidentNumber": "INC-002",
    "title": "Email Server Slow",
    "description": "Email server responding slowly to requests",
    "priority": "HIGH",
    "status": "ASSIGNED",
    "reporter": "admin@example.com",
    "assignedTo": "itsm.manager@example.com"
  },
  {
    "incidentNumber": "INC-003",
    "title": "VPN Connection Issues",
    "description": "Users unable to connect to corporate VPN",
    "priority": "HIGH",
    "status": "NEW",
    "reporter": "ops.manager@example.com"
  },
  {
    "incidentNumber": "INC-004",
    "title": "Printer Not Working",
    "description": "Office printer on 3rd floor not responding",
    "priority": "MEDIUM",
    "status": "RESOLVED",
    "reporter": "admin@example.com"
  },
  {
    "incidentNumber": "INC-005",
    "title": "Network Latency",
    "description": "High network latency observed in main office",
    "priority": "MEDIUM",
    "status": "CLOSED",
    "reporter": "ops.manager@example.com"
  }
]
```

---

## CMDB Service Seed Data

### Configuration Items
```json
[
  {
    "name": "Production-DB-01",
    "type": "Database",
    "description": "Primary production database server",
    "status": "ACTIVE",
    "owner": "cmdb.admin@example.com",
    "location": "Data Center A",
    "costCenter": "IT-001"
  },
  {
    "name": "Web-Server-01",
    "type": "Server",
    "description": "Primary web application server",
    "status": "ACTIVE",
    "owner": "cmdb.admin@example.com",
    "location": "Data Center A",
    "costCenter": "IT-001"
  },
  {
    "name": "Web-Server-02",
    "type": "Server",
    "description": "Secondary web application server",
    "status": "ACTIVE",
    "owner": "cmdb.admin@example.com",
    "location": "Data Center B",
    "costCenter": "IT-001"
  },
  {
    "name": "Email-Server",
    "type": "Server",
    "description": "Corporate email server",
    "status": "ACTIVE",
    "owner": "cmdb.admin@example.com",
    "location": "Data Center A",
    "costCenter": "IT-002"
  },
  {
    "name": "Backup-Storage",
    "type": "Storage",
    "description": "Backup storage system",
    "status": "ACTIVE",
    "owner": "cmdb.admin@example.com",
    "location": "Data Center C",
    "costCenter": "IT-001"
  },
  {
    "name": "Network-Switch-01",
    "type": "Network Device",
    "description": "Core network switch",
    "status": "ACTIVE",
    "owner": "ops.manager@example.com",
    "location": "Network Room",
    "costCenter": "IT-003"
  },
  {
    "name": "Firewall-01",
    "type": "Security Device",
    "description": "Primary firewall",
    "status": "ACTIVE",
    "owner": "ops.manager@example.com",
    "location": "Network Room",
    "costCenter": "IT-003"
  },
  {
    "name": "VPN-Gateway",
    "type": "Network Device",
    "description": "VPN gateway for remote access",
    "status": "INACTIVE",
    "owner": "ops.manager@example.com",
    "location": "Network Room",
    "costCenter": "IT-003"
  }
]
```

---

## ITOM Service Seed Data

### Discovered Assets
```json
[
  {
    "name": "Server-01",
    "assetType": "Server",
    "description": "Production web server",
    "ipAddress": "192.168.1.10",
    "hostname": "server-01.example.com",
    "osType": "Linux",
    "osVersion": "Ubuntu 22.04",
    "status": "MONITORED",
    "location": "Data Center A"
  },
  {
    "name": "Server-02",
    "assetType": "Server",
    "description": "Production application server",
    "ipAddress": "192.168.1.11",
    "hostname": "server-02.example.com",
    "osType": "Linux",
    "osVersion": "CentOS 8",
    "status": "MONITORED",
    "location": "Data Center A"
  },
  {
    "name": "Server-03",
    "assetType": "Server",
    "description": "Database server",
    "ipAddress": "192.168.1.12",
    "hostname": "db-server.example.com",
    "osType": "Linux",
    "osVersion": "Ubuntu 22.04",
    "status": "MONITORED",
    "location": "Data Center B"
  },
  {
    "name": "Workstation-01",
    "assetType": "Workstation",
    "description": "Admin workstation",
    "ipAddress": "192.168.1.100",
    "hostname": "admin-ws.example.com",
    "osType": "Windows",
    "osVersion": "Windows 11",
    "status": "DISCOVERED",
    "location": "Office A"
  },
  {
    "name": "Printer-01",
    "assetType": "Printer",
    "description": "Office printer",
    "ipAddress": "192.168.1.50",
    "hostname": "printer-01.example.com",
    "osType": "Embedded",
    "osVersion": "HP FW v5.2",
    "status": "MONITORED",
    "location": "Office A"
  },
  {
    "name": "Switch-01",
    "assetType": "Network Switch",
    "description": "Core network switch",
    "ipAddress": "192.168.1.1",
    "hostname": "switch-01.example.com",
    "osType": "Network OS",
    "osVersion": "IOS 15.2",
    "status": "MONITORED",
    "location": "Network Room"
  },
  {
    "name": "Firewall-01",
    "assetType": "Firewall",
    "description": "Primary firewall",
    "ipAddress": "192.168.1.2",
    "hostname": "firewall-01.example.com",
    "osType": "Security OS",
    "osVersion": "PAN-OS 10.1",
    "status": "MONITORED",
    "location": "Network Room"
  },
  {
    "name": "Router-01",
    "assetType": "Router",
    "description": "Core router",
    "ipAddress": "10.0.0.1",
    "hostname": "router-01.example.com",
    "osType": "Network OS",
    "osVersion": "IOS 16.3",
    "status": "DISCOVERED",
    "location": "Network Room"
  }
]
```

---

## ITAM Service Seed Data

### Hardware Assets
```json
[
  {
    "assetTag": "HW-001",
    "name": "Dell XPS 15",
    "assetType": "Laptop",
    "manufacturer": "Dell",
    "model": "XPS 15",
    "serialNumber": "DELL-XPS-001",
    "purchaseDate": "2023-01-15",
    "purchaseCost": 2500.00,
    "warrantyExpiryDate": "2026-01-15",
    "assignedTo": "admin@example.com",
    "status": "ACTIVE",
    "location": "Office A",
    "costCenter": "IT-001"
  },
  {
    "assetTag": "HW-002",
    "name": "MacBook Pro 16",
    "assetType": "Laptop",
    "manufacturer": "Apple",
    "model": "MacBook Pro 16",
    "serialNumber": "APPLE-MBP-002",
    "purchaseDate": "2023-03-20",
    "purchaseCost": 3500.00,
    "warrantyExpiryDate": "2026-03-20",
    "assignedTo": "itsm.manager@example.com",
    "status": "ACTIVE",
    "location": "Office B",
    "costCenter": "IT-001"
  },
  {
    "assetTag": "HW-003",
    "name": "HP ProBook 450",
    "assetType": "Laptop",
    "manufacturer": "HP",
    "model": "ProBook 450",
    "serialNumber": "HP-PB-003",
    "purchaseDate": "2023-05-10",
    "purchaseCost": 1200.00,
    "warrantyExpiryDate": "2026-05-10",
    "assignedTo": "cmdb.admin@example.com",
    "status": "ACTIVE",
    "location": "Office C",
    "costCenter": "IT-001"
  },
  {
    "assetTag": "HW-004",
    "name": "Lenovo ThinkPad",
    "assetType": "Laptop",
    "manufacturer": "Lenovo",
    "model": "ThinkPad X1",
    "serialNumber": "LENOVO-TP-004",
    "purchaseDate": "2023-06-15",
    "purchaseCost": 1500.00,
    "warrantyExpiryDate": "2026-06-15",
    "assignedTo": "ops.manager@example.com",
    "status": "ACTIVE",
    "location": "Office D",
    "costCenter": "IT-001"
  },
  {
    "assetTag": "HW-005",
    "name": "Dell Monitor 27",
    "assetType": "Monitor",
    "manufacturer": "Dell",
    "model": "U2720Q",
    "serialNumber": "DELL-MON-005",
    "purchaseDate": "2023-02-01",
    "purchaseCost": 450.00,
    "warrantyExpiryDate": "2026-02-01",
    "status": "ACTIVE",
    "location": "Office A",
    "costCenter": "IT-002"
  },
  {
    "assetTag": "HW-006",
    "name": "Logitech Keyboard",
    "assetType": "Keyboard",
    "manufacturer": "Logitech",
    "model": "MX Keys",
    "serialNumber": "LOGI-KB-006",
    "purchaseDate": "2023-02-15",
    "purchaseCost": 99.00,
    "warrantyExpiryDate": "2026-02-15",
    "status": "ACTIVE",
    "location": "Office A",
    "costCenter": "IT-002"
  },
  {
    "assetTag": "HW-007",
    "name": "Logitech Mouse",
    "assetType": "Mouse",
    "manufacturer": "Logitech",
    "model": "MX Master 3",
    "serialNumber": "LOGI-MS-007",
    "purchaseDate": "2023-02-20",
    "purchaseCost": 99.00,
    "warrantyExpiryDate": "2026-02-20",
    "status": "ACTIVE",
    "location": "Office A",
    "costCenter": "IT-002"
  },
  {
    "assetTag": "HW-008",
    "name": "Cisco IP Phone",
    "assetType": "Phone",
    "manufacturer": "Cisco",
    "model": "8861",
    "serialNumber": "CISCO-PH-008",
    "purchaseDate": "2023-04-01",
    "purchaseCost": 350.00,
    "warrantyExpiryDate": "2026-04-01",
    "status": "INACTIVE",
    "location": "Office B",
    "costCenter": "IT-003"
  },
  {
    "assetTag": "HW-009",
    "name": "Apple iPad Pro",
    "assetType": "Tablet",
    "manufacturer": "Apple",
    "model": "iPad Pro 12.9",
    "serialNumber": "APPLE-IPD-009",
    "purchaseDate": "2023-07-01",
    "purchaseCost": 1200.00,
    "warrantyExpiryDate": "2026-07-01",
    "assignedTo": "admin@example.com",
    "status": "ACTIVE",
    "location": "Office A",
    "costCenter": "IT-002"
  },
  {
    "assetTag": "HW-010",
    "name": "Samsung 4K Monitor",
    "assetType": "Monitor",
    "manufacturer": "Samsung",
    "model": "LU28E590DS",
    "serialNumber": "SAMSUNG-MON-010",
    "purchaseDate": "2023-08-10",
    "purchaseCost": 350.00,
    "warrantyExpiryDate": "2026-08-10",
    "status": "RETIRED",
    "location": "Storage",
    "costCenter": "IT-002"
  }
]
```

---

## SQL Scripts for Seed Data

### PostgreSQL - ITSM Service

```sql
-- Insert test users
INSERT INTO users (id, email, password, role, created_at) VALUES
('user-001', 'admin@example.com', 'hashed_password', 'ADMIN', NOW()),
('user-002', 'itsm.manager@example.com', 'hashed_password', 'ITSM_MANAGER', NOW()),
('user-003', 'cmdb.admin@example.com', 'hashed_password', 'CMDB_ADMIN', NOW()),
('user-004', 'ops.manager@example.com', 'hashed_password', 'OPS_MANAGER', NOW());

-- Insert incidents
INSERT INTO incidents (id, incident_number, title, description, priority, status, reporter, assigned_to, created_at) VALUES
('inc-001', 'INC-001', 'Production Database Down', 'Critical production database is not responding', 'CRITICAL', 'IN_PROGRESS', 'admin@example.com', 'itsm.manager@example.com', NOW()),
('inc-002', 'INC-002', 'Email Server Slow', 'Email server responding slowly to requests', 'HIGH', 'ASSIGNED', 'admin@example.com', 'itsm.manager@example.com', NOW()),
('inc-003', 'INC-003', 'VPN Connection Issues', 'Users unable to connect to corporate VPN', 'HIGH', 'NEW', 'ops.manager@example.com', NULL, NOW()),
('inc-004', 'INC-004', 'Printer Not Working', 'Office printer on 3rd floor not responding', 'MEDIUM', 'RESOLVED', 'admin@example.com', NULL, NOW()),
('inc-005', 'INC-005', 'Network Latency', 'High network latency observed in main office', 'MEDIUM', 'CLOSED', 'ops.manager@example.com', NULL, NOW());
```

### PostgreSQL - CMDB Service

```sql
-- Insert configuration items
INSERT INTO configuration_items (id, name, type, description, status, owner, location, cost_center, created_at) VALUES
('ci-001', 'Production-DB-01', 'Database', 'Primary production database server', 'ACTIVE', 'cmdb.admin@example.com', 'Data Center A', 'IT-001', NOW()),
('ci-002', 'Web-Server-01', 'Server', 'Primary web application server', 'ACTIVE', 'cmdb.admin@example.com', 'Data Center A', 'IT-001', NOW()),
('ci-003', 'Web-Server-02', 'Server', 'Secondary web application server', 'ACTIVE', 'cmdb.admin@example.com', 'Data Center B', 'IT-001', NOW()),
('ci-004', 'Email-Server', 'Server', 'Corporate email server', 'ACTIVE', 'cmdb.admin@example.com', 'Data Center A', 'IT-002', NOW()),
('ci-005', 'Backup-Storage', 'Storage', 'Backup storage system', 'ACTIVE', 'cmdb.admin@example.com', 'Data Center C', 'IT-001', NOW()),
('ci-006', 'Network-Switch-01', 'Network Device', 'Core network switch', 'ACTIVE', 'ops.manager@example.com', 'Network Room', 'IT-003', NOW()),
('ci-007', 'Firewall-01', 'Security Device', 'Primary firewall', 'ACTIVE', 'ops.manager@example.com', 'Network Room', 'IT-003', NOW()),
('ci-008', 'VPN-Gateway', 'Network Device', 'VPN gateway for remote access', 'INACTIVE', 'ops.manager@example.com', 'Network Room', 'IT-003', NOW());
```

### PostgreSQL - ITOM Service

```sql
-- Insert discovered assets
INSERT INTO discovered_assets (id, name, asset_type, description, ip_address, hostname, os_type, os_version, status, location, created_at) VALUES
('asset-001', 'Server-01', 'Server', 'Production web server', '192.168.1.10', 'server-01.example.com', 'Linux', 'Ubuntu 22.04', 'MONITORED', 'Data Center A', NOW()),
('asset-002', 'Server-02', 'Server', 'Production application server', '192.168.1.11', 'server-02.example.com', 'Linux', 'CentOS 8', 'MONITORED', 'Data Center A', NOW()),
('asset-003', 'Server-03', 'Server', 'Database server', '192.168.1.12', 'db-server.example.com', 'Linux', 'Ubuntu 22.04', 'MONITORED', 'Data Center B', NOW()),
('asset-004', 'Workstation-01', 'Workstation', 'Admin workstation', '192.168.1.100', 'admin-ws.example.com', 'Windows', 'Windows 11', 'DISCOVERED', 'Office A', NOW()),
('asset-005', 'Printer-01', 'Printer', 'Office printer', '192.168.1.50', 'printer-01.example.com', 'Embedded', 'HP FW v5.2', 'MONITORED', 'Office A', NOW()),
('asset-006', 'Switch-01', 'Network Switch', 'Core network switch', '192.168.1.1', 'switch-01.example.com', 'Network OS', 'IOS 15.2', 'MONITORED', 'Network Room', NOW()),
('asset-007', 'Firewall-01', 'Firewall', 'Primary firewall', '192.168.1.2', 'firewall-01.example.com', 'Security OS', 'PAN-OS 10.1', 'MONITORED', 'Network Room', NOW()),
('asset-008', 'Router-01', 'Router', 'Core router', '10.0.0.1', 'router-01.example.com', 'Network OS', 'IOS 16.3', 'DISCOVERED', 'Network Room', NOW());
```

### PostgreSQL - ITAM Service

```sql
-- Insert hardware assets
INSERT INTO hardware_assets (id, asset_tag, name, asset_type, manufacturer, model, serial_number, purchase_date, purchase_cost, warranty_expiry_date, assigned_to, status, location, cost_center, created_at) VALUES
('hw-001', 'HW-001', 'Dell XPS 15', 'Laptop', 'Dell', 'XPS 15', 'DELL-XPS-001', '2023-01-15', 2500.00, '2026-01-15', 'admin@example.com', 'ACTIVE', 'Office A', 'IT-001', NOW()),
('hw-002', 'HW-002', 'MacBook Pro 16', 'Laptop', 'Apple', 'MacBook Pro 16', 'APPLE-MBP-002', '2023-03-20', 3500.00, '2026-03-20', 'itsm.manager@example.com', 'ACTIVE', 'Office B', 'IT-001', NOW()),
('hw-003', 'HW-003', 'HP ProBook 450', 'Laptop', 'HP', 'ProBook 450', 'HP-PB-003', '2023-05-10', 1200.00, '2026-05-10', 'cmdb.admin@example.com', 'ACTIVE', 'Office C', 'IT-001', NOW()),
('hw-004', 'HW-004', 'Lenovo ThinkPad', 'Laptop', 'Lenovo', 'ThinkPad X1', 'LENOVO-TP-004', '2023-06-15', 1500.00, '2026-06-15', 'ops.manager@example.com', 'ACTIVE', 'Office D', 'IT-001', NOW()),
('hw-005', 'HW-005', 'Dell Monitor 27', 'Monitor', 'Dell', 'U2720Q', 'DELL-MON-005', '2023-02-01', 450.00, '2026-02-01', NULL, 'ACTIVE', 'Office A', 'IT-002', NOW()),
('hw-006', 'HW-006', 'Logitech Keyboard', 'Keyboard', 'Logitech', 'MX Keys', 'LOGI-KB-006', '2023-02-15', 99.00, '2026-02-15', NULL, 'ACTIVE', 'Office A', 'IT-002', NOW()),
('hw-007', 'HW-007', 'Logitech Mouse', 'Mouse', 'Logitech', 'MX Master 3', 'LOGI-MS-007', '2023-02-20', 99.00, '2026-02-20', NULL, 'ACTIVE', 'Office A', 'IT-002', NOW()),
('hw-008', 'HW-008', 'Cisco IP Phone', 'Phone', 'Cisco', '8861', 'CISCO-PH-008', '2023-04-01', 350.00, '2026-04-01', NULL, 'INACTIVE', 'Office B', 'IT-003', NOW()),
('hw-009', 'HW-009', 'Apple iPad Pro', 'Tablet', 'Apple', 'iPad Pro 12.9', 'APPLE-IPD-009', '2023-07-01', 1200.00, '2026-07-01', 'admin@example.com', 'ACTIVE', 'Office A', 'IT-002', NOW()),
('hw-010', 'HW-010', 'Samsung 4K Monitor', 'Monitor', 'Samsung', 'LU28E590DS', 'SAMSUNG-MON-010', '2023-08-10', 350.00, '2026-08-10', NULL, 'RETIRED', 'Storage', 'IT-002', NOW());
```

---

## How to Load Seed Data

### Option 1: Using SQL Scripts
```bash
# ITSM Service
psql -h localhost -U postgres -d itsm_db < seed_itsm.sql

# CMDB Service
psql -h localhost -U postgres -d cmdb_db < seed_cmdb.sql

# ITOM Service
psql -h localhost -U postgres -d itom_db < seed_itom.sql

# ITAM Service
psql -h localhost -U postgres -d itam_db < seed_itam.sql
```

### Option 2: Using API Endpoints
```bash
# Create incidents
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d @seed_incidents.json \
  http://localhost:8001/api/v1/incidents

# Create CIs
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d @seed_cis.json \
  http://localhost:8000/api/v1/cis

# Create assets
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d @seed_assets.json \
  http://localhost:8002/api/v1/assets

# Create hardware
curl -X POST -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d @seed_hardware.json \
  http://localhost:8003/api/v1/assets
```

---

## Summary

### Test Users (4 Total)
- ✅ admin@example.com (Admin@123456)
- ✅ itsm.manager@example.com (ITSM@123456)
- ✅ cmdb.admin@example.com (CMDB@123456)
- ✅ ops.manager@example.com (Ops@123456)

### Seed Data
- ✅ 5 Incidents (ITSM)
- ✅ 8 Configuration Items (CMDB)
- ✅ 8 Discovered Assets (ITOM)
- ✅ 10 Hardware Assets (ITAM)

**Total**: 31 seed records across all services

---

**Status**: ✅ SEED DATA READY  
**Last Updated**: July 12, 2026
