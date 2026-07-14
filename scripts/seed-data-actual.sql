-- ============================================================================
-- Snowrepo Seed Data Script (Actual Schema)
-- Loads sample data matching actual table schemas
-- ============================================================================

-- ============================================================================
-- 1. ITSM DATABASE - INCIDENTS
-- ============================================================================

\c itsm_db

INSERT INTO incidents (id, incident_number, title, description, priority, status, assigned_to, created_by, created_at, updated_by, updated_at, version, affected_service, impact_level, urgency_level, reporter, resolved_at)
VALUES
  ('550e8400-e29b-41d4-a716-446655440001', 'INC-001', 'Email Server Down', 'Critical email server outage affecting all users', 'CRITICAL', 'NEW', 'itsm.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP, 'admin@example.com', CURRENT_TIMESTAMP, 0, 'Email Service', 'HIGH', 'URGENT', 'admin@example.com', NULL),
  ('550e8400-e29b-41d4-a716-446655440002', 'INC-002', 'Network Connectivity Issues', 'Intermittent network connectivity in Building A', 'HIGH', 'IN_PROGRESS', 'ops.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '2 days', 'ops.manager@example.com', CURRENT_TIMESTAMP, 1, 'Network', 'MEDIUM', 'HIGH', 'admin@example.com', NULL),
  ('550e8400-e29b-41d4-a716-446655440003', 'INC-003', 'Printer Not Responding', 'HP Printer on 3rd floor not responding to print jobs', 'MEDIUM', 'RESOLVED', 'itsm.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '5 days', 'itsm.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '1 day', 2, 'Printing', 'LOW', 'MEDIUM', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '1 day'),
  ('550e8400-e29b-41d4-a716-446655440004', 'INC-004', 'VPN Connection Failures', 'Remote users unable to connect to VPN', 'HIGH', 'IN_PROGRESS', 'ops.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '1 day', 'ops.manager@example.com', CURRENT_TIMESTAMP, 1, 'VPN', 'HIGH', 'HIGH', 'admin@example.com', NULL),
  ('550e8400-e29b-41d4-a716-446655440005', 'INC-005', 'Database Performance Degradation', 'Slow database queries affecting application performance', 'HIGH', 'NEW', 'itsm.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '3 hours', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '3 hours', 0, 'Database', 'HIGH', 'HIGH', 'admin@example.com', NULL),
  ('550e8400-e29b-41d4-a716-446655440006', 'INC-006', 'Software License Expired', 'Antivirus software license expired on 10 servers', 'MEDIUM', 'NEW', 'admin@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '2 hours', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '2 hours', 0, 'Software', 'MEDIUM', 'MEDIUM', 'admin@example.com', NULL),
  ('550e8400-e29b-41d4-a716-446655440007', 'INC-007', 'User Account Locked', 'Multiple user accounts locked due to failed login attempts', 'LOW', 'RESOLVED', 'itsm.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '7 days', 'itsm.manager@example.com', CURRENT_TIMESTAMP - INTERVAL '6 days', 1, 'Access Control', 'LOW', 'LOW', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '6 days'),
  ('550e8400-e29b-41d4-a716-446655440008', 'INC-008', 'Backup Failure', 'Nightly backup job failed for production database', 'CRITICAL', 'NEW', 'ops.manager@example.com', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '4 hours', 'admin@example.com', CURRENT_TIMESTAMP - INTERVAL '4 hours', 0, 'Backup', 'CRITICAL', 'URGENT', 'admin@example.com', NULL);

-- ============================================================================
-- 2. CMDB DATABASE - CONFIGURATION ITEMS
-- ============================================================================

\c cmdb_db

-- Check actual schema first
SELECT column_name FROM information_schema.columns WHERE table_name='configuration_items' ORDER BY ordinal_position;

-- ============================================================================
-- 3. ITOM DATABASE - DISCOVERED ASSETS
-- ============================================================================

\c itom_db

-- Check actual schema first
SELECT column_name FROM information_schema.columns WHERE table_name='discovered_assets' ORDER BY ordinal_position;

-- ============================================================================
-- 4. ITAM DATABASE - HARDWARE ASSETS
-- ============================================================================

\c itam_db

-- Check actual schema first
SELECT column_name FROM information_schema.columns WHERE table_name='hardware_assets' ORDER BY ordinal_position;

-- ============================================================================
-- Seed Data Load Complete
-- ============================================================================
