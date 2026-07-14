-- ============================================================================
-- Snowrepo Database Initialization Script
-- Creates all databases and tables for microservices
-- ============================================================================

-- ============================================================================
-- 1. CREATE DATABASES
-- ============================================================================

CREATE DATABASE IF NOT EXISTS itsm_db
  WITH ENCODING 'UTF8'
  LC_COLLATE 'en_US.utf8'
  LC_CTYPE 'en_US.utf8';

CREATE DATABASE IF NOT EXISTS cmdb_db
  WITH ENCODING 'UTF8'
  LC_COLLATE 'en_US.utf8'
  LC_CTYPE 'en_US.utf8';

CREATE DATABASE IF NOT EXISTS itom_db
  WITH ENCODING 'UTF8'
  LC_COLLATE 'en_US.utf8'
  LC_CTYPE 'en_US.utf8';

CREATE DATABASE IF NOT EXISTS itam_db
  WITH ENCODING 'UTF8'
  LC_COLLATE 'en_US.utf8'
  LC_CTYPE 'en_US.utf8';

-- ============================================================================
-- 2. ITSM DATABASE TABLES
-- ============================================================================

\c itsm_db

CREATE TABLE IF NOT EXISTS incidents (
  id UUID PRIMARY KEY,
  incident_number VARCHAR(50) NOT NULL UNIQUE,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  priority VARCHAR(20) NOT NULL CHECK (priority IN ('CRITICAL', 'HIGH', 'MEDIUM', 'LOW')),
  status VARCHAR(20) NOT NULL CHECK (status IN ('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED')),
  assigned_to VARCHAR(255),
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(255),
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version BIGINT DEFAULT 0,
  CONSTRAINT check_dates CHECK (updated_at >= created_at)
);

CREATE INDEX IF NOT EXISTS idx_incidents_number ON incidents(incident_number);
CREATE INDEX IF NOT EXISTS idx_incidents_status ON incidents(status);
CREATE INDEX IF NOT EXISTS idx_incidents_priority ON incidents(priority);
CREATE INDEX IF NOT EXISTS idx_incidents_assigned_to ON incidents(assigned_to);
CREATE INDEX IF NOT EXISTS idx_incidents_created_at ON incidents(created_at);

-- ============================================================================
-- 3. CMDB DATABASE TABLES
-- ============================================================================

\c cmdb_db

CREATE TABLE IF NOT EXISTS configuration_items (
  id UUID PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  type VARCHAR(50) NOT NULL CHECK (type IN ('SERVER', 'DATABASE', 'APPLICATION', 'NETWORK', 'STORAGE', 'OTHER')),
  description TEXT,
  status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'RETIRED', 'PENDING')),
  owner VARCHAR(255),
  location VARCHAR(255),
  cost DECIMAL(15, 2),
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(255),
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version BIGINT DEFAULT 0,
  CONSTRAINT check_dates CHECK (updated_at >= created_at)
);

CREATE INDEX IF NOT EXISTS idx_ci_name ON configuration_items(name);
CREATE INDEX IF NOT EXISTS idx_ci_type ON configuration_items(type);
CREATE INDEX IF NOT EXISTS idx_ci_status ON configuration_items(status);
CREATE INDEX IF NOT EXISTS idx_ci_owner ON configuration_items(owner);
CREATE INDEX IF NOT EXISTS idx_ci_created_at ON configuration_items(created_at);

-- ============================================================================
-- 4. ITOM DATABASE TABLES
-- ============================================================================

\c itom_db

CREATE TABLE IF NOT EXISTS discovered_assets (
  id UUID PRIMARY KEY,
  asset_name VARCHAR(255) NOT NULL,
  asset_type VARCHAR(50) NOT NULL CHECK (asset_type IN ('SERVER', 'WORKSTATION', 'PRINTER', 'ROUTER', 'SWITCH', 'FIREWALL', 'OTHER')),
  ip_address VARCHAR(45),
  mac_address VARCHAR(17),
  status VARCHAR(20) NOT NULL CHECK (status IN ('DISCOVERED', 'VERIFIED', 'ARCHIVED', 'UNKNOWN')),
  discovery_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_seen TIMESTAMP,
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(255),
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version BIGINT DEFAULT 0,
  CONSTRAINT check_dates CHECK (updated_at >= created_at)
);

CREATE INDEX IF NOT EXISTS idx_asset_name ON discovered_assets(asset_name);
CREATE INDEX IF NOT EXISTS idx_asset_type ON discovered_assets(asset_type);
CREATE INDEX IF NOT EXISTS idx_asset_status ON discovered_assets(status);
CREATE INDEX IF NOT EXISTS idx_asset_ip ON discovered_assets(ip_address);
CREATE INDEX IF NOT EXISTS idx_asset_mac ON discovered_assets(mac_address);
CREATE INDEX IF NOT EXISTS idx_asset_discovery_date ON discovered_assets(discovery_date);

-- ============================================================================
-- 5. ITAM DATABASE TABLES
-- ============================================================================

\c itam_db

CREATE TABLE IF NOT EXISTS hardware_assets (
  id UUID PRIMARY KEY,
  asset_tag VARCHAR(50) NOT NULL UNIQUE,
  asset_name VARCHAR(255) NOT NULL,
  asset_type VARCHAR(50) NOT NULL CHECK (asset_type IN ('LAPTOP', 'DESKTOP', 'SERVER', 'PRINTER', 'MONITOR', 'KEYBOARD', 'MOUSE', 'OTHER')),
  status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'RETIRED', 'REPAIR', 'LOST')),
  assigned_to VARCHAR(255),
  purchase_date DATE,
  warranty_expiry DATE,
  cost DECIMAL(15, 2),
  location VARCHAR(255),
  created_by VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_by VARCHAR(255),
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version BIGINT DEFAULT 0,
  CONSTRAINT check_dates CHECK (updated_at >= created_at)
);

CREATE INDEX IF NOT EXISTS idx_hw_asset_tag ON hardware_assets(asset_tag);
CREATE INDEX IF NOT EXISTS idx_hw_asset_name ON hardware_assets(asset_name);
CREATE INDEX IF NOT EXISTS idx_hw_asset_type ON hardware_assets(asset_type);
CREATE INDEX IF NOT EXISTS idx_hw_asset_status ON hardware_assets(status);
CREATE INDEX IF NOT EXISTS idx_hw_assigned_to ON hardware_assets(assigned_to);
CREATE INDEX IF NOT EXISTS idx_hw_created_at ON hardware_assets(created_at);

-- ============================================================================
-- 6. GRANT PERMISSIONS
-- ============================================================================

-- ITSM Database
\c itsm_db
GRANT ALL PRIVILEGES ON DATABASE itsm_db TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;

-- CMDB Database
\c cmdb_db
GRANT ALL PRIVILEGES ON DATABASE cmdb_db TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;

-- ITOM Database
\c itom_db
GRANT ALL PRIVILEGES ON DATABASE itom_db TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;

-- ITAM Database
\c itam_db
GRANT ALL PRIVILEGES ON DATABASE itam_db TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;

-- ============================================================================
-- Initialization Complete
-- ============================================================================
-- All databases and tables have been created successfully.
-- Ready for seed data insertion.
