-- ============================================================================
-- Snowrepo Seed Data Script
-- Loads sample data into all microservice databases
-- ============================================================================

-- ============================================================================
-- 1. ITSM DATABASE - INCIDENTS
-- ============================================================================

\c itsm_db

INSERT INTO incidents (id, incident_number, title, description, priority, status, assigned_to, created_by, created_at, updated_by, updated_at, version)
VALUES
  ('550e8400-e29b-41d4-a716-446655440001', 'INC-001', 'Email Server Down', 'Critical email server outage affecting all users', 'CRITICAL', 'OPEN', 'itsm.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP, 'admin@example.com', CURRENT_TIMESTAMP, 0),
  ('550e8400-e29b-41d4-a716-446655440002', 'INC-002', 'Network Connectivity Issues', 'Intermittent network connectivity in Building A', 'HIGH', 'IN_PROGRESS', 'ops.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '2 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 1),
  ('550e8400-e29b-41d4-a716-446655440003', 'INC-003', 'Printer Not Responding', 'HP Printer on 3rd floor not responding to print jobs', 'MEDIUM', 'RESOLVED', 'itsm.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '5 days', 'itsm.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '1 day', 2),
  ('550e8400-e29b-41d4-a716-446655440004', 'INC-004', 'VPN Connection Failures', 'Remote users unable to connect to VPN', 'HIGH', 'IN_PROGRESS', 'ops.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '1 day', 'ops.manager@example.com', CURRENT_TIMESTAMP, 1),
  ('550e8400-e29b-41d4-a716-446655440005', 'INC-005', 'Database Performance Degradation', 'Slow database queries affecting application performance', 'HIGH', 'OPEN', 'itsm.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '3 hours', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '3 hours', 0),
  ('550e8400-e29b-41d4-a716-446655440006', 'INC-006', 'Software License Expired', 'Antivirus software license expired on 10 servers', 'MEDIUM', 'OPEN', 'admin@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '2 hours', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '2 hours', 0),
  ('550e8400-e29b-41d4-a716-446655440007', 'INC-007', 'User Account Locked', 'Multiple user accounts locked due to failed login attempts', 'LOW', 'RESOLVED', 'itsm.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '7 days', 'itsm.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '6 days', 1),
  ('550e8400-e29b-41d4-a716-446655440008', 'INC-008', 'Backup Failure', 'Nightly backup job failed for production database', 'CRITICAL', 'OPEN', 'ops.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '4 hours', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '4 hours', 0);

-- ============================================================================
-- 2. CMDB DATABASE - CONFIGURATION ITEMS
-- ============================================================================

\c cmdb_db

INSERT INTO configuration_items (id, name, type, description, status, owner, location, cost, created_by, created_at, updated_by, updated_at, version)
VALUES
  ('650e8400-e29b-41d4-a716-446655440001', 'Production Web Server 1', 'SERVER', 'Primary web server for production environment', 'ACTIVE', 'ops.manager@example.com', 'Data Center A - Rack 5', 5000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '180 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 5),
  ('650e8400-e29b-41d4-a716-446655440002', 'Production Web Server 2', 'SERVER', 'Secondary web server for production environment', 'ACTIVE', 'ops.manager@example.com', 'Data Center A - Rack 6', 5000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '180 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 4),
  ('650e8400-e29b-41d4-a716-446655440003', 'Production Database Server', 'DATABASE', 'Primary PostgreSQL database server', 'ACTIVE', 'ops.manager@example.com', 'Data Center A - Rack 7', 8000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '200 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 6),
  ('650e8400-e29b-41d4-a716-446655440004', 'Backup Database Server', 'DATABASE', 'Backup PostgreSQL database server', 'ACTIVE', 'ops.manager@example.com', 'Data Center B - Rack 3', 8000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '200 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 3),
  ('650e8400-e29b-41d4-a716-446655440005', 'Email Server', 'APPLICATION', 'Microsoft Exchange email server', 'ACTIVE', 'itsm.manager@example.com', 'Data Center A - Rack 8', 3000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '150 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 2),
  ('650e8400-e29b-41d4-a716-446655440006', 'File Server', 'STORAGE', 'Network file storage server', 'ACTIVE', 'ops.manager@example.com', 'Data Center A - Rack 9', 4000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '120 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 1),
  ('650e8400-e29b-41d4-a716-446655440007', 'Network Switch - Core', 'NETWORK', 'Core network switch for data center', 'ACTIVE', 'ops.manager@example.com', 'Data Center A - Network Room', 6000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '300 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 2),
  ('650e8400-e29b-41d4-a716-446655440008', 'Firewall - Primary', 'NETWORK', 'Primary firewall for network security', 'ACTIVE', 'ops.manager@example.com', 'Data Center A - Network Room', 7000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '250 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 3),
  ('650e8400-e29b-41d4-a716-446655440009', 'Load Balancer', 'NETWORK', 'Load balancer for web servers', 'ACTIVE', 'ops.manager@example.com', 'Data Center A - Network Room', 5500.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '180 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 1),
  ('650e8400-e29b-41d4-a716-446655440010', 'Backup Storage Array', 'STORAGE', 'SAN storage for backups', 'INACTIVE', 'ops.manager@example.com', 'Data Center B - Rack 5', 10000.00, 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '365 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '30 days', 0);

-- ============================================================================
-- 3. ITOM DATABASE - DISCOVERED ASSETS
-- ============================================================================

\c itom_db

INSERT INTO discovered_assets (id, asset_name, asset_type, ip_address, mac_address, status, discovery_date, last_seen, created_by, created_at, updated_by, updated_at, version)
VALUES
  ('750e8400-e29b-41d4-a716-446655440001', 'Server-01', 'SERVER', '192.168.1.10', '00:1A:2B:3C:4D:5E', 'VERIFIED', CURRENT_TIMESTAMP - INTERVAL '30 days', CURRENT_TIMESTAMP, 'ops.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '30 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 5),
  ('750e8400-e29b-41d4-a716-446655440002', 'Server-02', 'SERVER', '192.168.1.11', '00:1A:2B:3C:4D:5F', 'VERIFIED', CURRENT_TIMESTAMP - INTERVAL '30 days', CURRENT_TIMESTAMP, 'ops.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '30 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 4),
  ('750e8400-e29b-41d4-a716-446655440003', 'Workstation-01', 'WORKSTATION', '192.168.1.100', '00:1A:2B:3C:4D:60', 'VERIFIED', CURRENT_TIMESTAMP - INTERVAL '15 days', CURRENT_TIMESTAMP, 'ops.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '15 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 2),
  ('750e8400-e29b-41d4-a716-446655440004', 'Workstation-02', 'WORKSTATION', '192.168.1.101', '00:1A:2B:3C:4D:61', 'VERIFIED', CURRENT_TIMESTAMP - INTERVAL '15 days', CURRENT_TIMESTAMP, 'ops.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '15 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 2),
  ('750e8400-e29b-41d4-a716-446655440005', 'Router-Core', 'ROUTER', '192.168.0.1', '00:1A:2B:3C:4D:62', 'VERIFIED', CURRENT_TIMESTAMP - INTERVAL '60 days', CURRENT_TIMESTAMP, 'ops.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '60 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 3),
  ('750e8400-e29b-41d4-a716-446655440006', 'Switch-Floor1', 'SWITCH', '192.168.1.200', '00:1A:2B:3C:4D:63', 'VERIFIED', CURRENT_TIMESTAMP - INTERVAL '45 days', CURRENT_TIMESTAMP, 'ops.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '45 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 2),
  ('750e8400-e29b-41d4-a716-446655440007', 'Printer-Office', 'PRINTER', '192.168.1.150', '00:1A:2B:3C:4D:64', 'VERIFIED', CURRENT_TIMESTAMP - INTERVAL '20 days', CURRENT_TIMESTAMP, 'ops.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '20 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 1),
  ('750e8400-e29b-41d4-a716-446655440008', 'Firewall-Main', 'FIREWALL', '192.168.0.2', '00:1A:2B:3C:4D:65', 'VERIFIED', CURRENT_TIMESTAMP - INTERVAL '90 days', CURRENT_TIMESTAMP, 'ops.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '90 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 4);

-- ============================================================================
-- 4. ITAM DATABASE - HARDWARE ASSETS
-- ============================================================================

\c itam_db

INSERT INTO hardware_assets (id, asset_tag, asset_name, asset_type, status, assigned_to, purchase_date, warranty_expiry, cost, location, created_by, created_at, updated_by, updated_at, version)
VALUES
  ('850e8400-e29b-41d4-a716-446655440001', 'LAPTOP-001', 'Dell Latitude 5520', 'LAPTOP', 'ACTIVE', 'itsm.manager@example.com', '2023-06-15', '2026-06-15', 1200.00, 'Office - Desk 101', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '365 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 2),
  ('850e8400-e29b-41d4-a716-446655440002', 'LAPTOP-002', 'HP EliteBook 850', 'LAPTOP', 'ACTIVE', 'ops.manager@example.com', '2023-07-20', '2026-07-20', 1300.00, 'Office - Desk 102', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '350 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 1),
  ('850e8400-e29b-41d4-a716-446655440003', 'DESKTOP-001', 'Dell OptiPlex 7090', 'DESKTOP', 'ACTIVE', 'admin@example.com', '2023-01-10', '2026-01-10', 900.00, 'Office - Desk 103', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '400 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 3),
  ('850e8400-e29b-41d4-a716-446655440004', 'DESKTOP-002', 'HP ProDesk 600', 'DESKTOP', 'ACTIVE', 'itsm.manager@example.com', '2023-02-15', '2026-02-15', 850.00, 'Office - Desk 104', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '390 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 2),
  ('850e8400-e29b-41d4-a716-446655440005', 'MONITOR-001', 'Dell U2722D 27"', 'MONITOR', 'ACTIVE', 'itsm.manager@example.com', '2023-03-20', '2026-03-20', 400.00, 'Office - Desk 101', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '380 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 1),
  ('850e8400-e29b-41d4-a716-446655440006', 'MONITOR-002', 'LG 27UP550', 'MONITOR', 'ACTIVE', 'ops.manager@example.com', '2023-04-10', '2026-04-10', 450.00, 'Office - Desk 102', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '370 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 1),
  ('850e8400-e29b-41d4-a716-446655440007', 'KEYBOARD-001', 'Logitech MX Keys', 'KEYBOARD', 'ACTIVE', 'itsm.manager@example.com', '2023-05-05', '2026-05-05', 100.00, 'Office - Desk 101', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '360 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 0),
  ('850e8400-e29b-41d4-a716-446655440008', 'MOUSE-001', 'Logitech MX Master 3S', 'MOUSE', 'ACTIVE', 'ops.manager@example.com', '2023-05-10', '2026-05-10', 100.00, 'Office - Desk 102', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '355 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 0),
  ('850e8400-e29b-41d4-a716-446655440009', 'SERVER-001', 'Dell PowerEdge R750', 'SERVER', 'ACTIVE', 'ops.manager@example.com', '2022-12-01', '2025-12-01', 5000.00, 'Data Center A - Rack 5', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '420 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP, 4),
  ('850e8400-e29b-41d4-a716-446655440010', 'PRINTER-001', 'HP LaserJet Pro M404n', 'PRINTER', 'INACTIVE', 'admin@example.com', '2021-08-15', '2024-08-15', 500.00, 'Office - Storage', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '600 days', 'cmdb.admin@example.com', CURRENT_TIMESTAMP - INTERVAL '180 days', 0);

-- ============================================================================
-- Seed Data Load Complete
-- ============================================================================
-- Summary:
-- - ITSM: 8 incidents loaded
-- - CMDB: 10 configuration items loaded
-- - ITOM: 8 discovered assets loaded
-- - ITAM: 10 hardware assets loaded
-- Total: 36 records inserted
-- ============================================================================
