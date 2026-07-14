# Database Initialization Guide

This guide provides instructions for initializing all databases and tables for the Snowrepo microservices platform.

## Overview

The database initialization process creates:
- **4 PostgreSQL Databases**: itsm_db, cmdb_db, itom_db, itam_db
- **4 Entity Tables**: incidents, configuration_items, discovered_assets, hardware_assets
- **Indexes**: For optimal query performance
- **Permissions**: Proper user access control

## Prerequisites

- Docker installed and running
- PostgreSQL container (`itsm-postgres`) running
- Access to the scripts directory

## Database Structure

### ITSM Database (itsm_db)
**Table**: `incidents`
- Stores incident/ticket information
- Columns: id, incident_number, title, description, priority, status, assigned_to, created_by, created_at, updated_by, updated_at, version
- Indexes: incident_number, status, priority, assigned_to, created_at

### CMDB Database (cmdb_db)
**Table**: `configuration_items`
- Stores configuration management database items
- Columns: id, name, type, description, status, owner, location, cost, created_by, created_at, updated_by, updated_at, version
- Indexes: name, type, status, owner, created_at

### ITOM Database (itom_db)
**Table**: `discovered_assets`
- Stores IT operations management discovered assets
- Columns: id, asset_name, asset_type, ip_address, mac_address, status, discovery_date, last_seen, created_by, created_at, updated_by, updated_at, version
- Indexes: asset_name, asset_type, status, ip_address, mac_address, discovery_date

### ITAM Database (itam_db)
**Table**: `hardware_assets`
- Stores IT asset management hardware assets
- Columns: id, asset_tag, asset_name, asset_type, status, assigned_to, purchase_date, warranty_expiry, cost, location, created_by, created_at, updated_by, updated_at, version
- Indexes: asset_tag, asset_name, asset_type, status, assigned_to, created_at

## Initialization Methods

### Method 1: Windows Batch Script

**File**: `scripts/init-databases.bat`

```bash
cd scripts
init-databases.bat
```

**Features**:
- Automatic Docker status checking
- Container health verification
- Auto-start container if needed
- Detailed progress logging
- Error handling and reporting

### Method 2: Linux/Mac Bash Script

**File**: `scripts/init-databases.sh`

```bash
cd scripts
chmod +x init-databases.sh
./init-databases.sh
```

**Features**:
- Cross-platform compatibility
- POSIX-compliant shell script
- Automatic container management
- Colored output for readability

### Method 3: Python Script

**File**: `scripts/init_databases.py`

```bash
cd scripts
python init_databases.py
```

**Features**:
- Cross-platform (Windows, Linux, Mac)
- Colored console output
- Detailed error messages
- No external dependencies (uses subprocess)

### Method 4: Direct SQL Execution

**File**: `scripts/init-databases.sql`

Execute directly in PostgreSQL:

```bash
docker exec -i itsm-postgres psql -U admin -f /path/to/init-databases.sql
```

Or using psql client:

```bash
psql -h localhost -U admin -f scripts/init-databases.sql
```

## PostgreSQL Connection Details

- **Host**: localhost
- **Port**: 5432
- **User**: admin
- **Password**: secret
- **Databases**: itsm_db, cmdb_db, itom_db, itam_db

## Verification

### Check Databases

```bash
docker exec itsm-postgres psql -U admin -l
```

Expected output:
```
 cmdb_db   | admin | UTF8
 itam_db   | admin | UTF8
 itom_db   | admin | UTF8
 itsm_db   | admin | UTF8
```

### Check Tables

```bash
# ITSM Database
docker exec itsm-postgres psql -U admin -d itsm_db -c "\dt"

# CMDB Database
docker exec itsm-postgres psql -U admin -d cmdb_db -c "\dt"

# ITOM Database
docker exec itsm-postgres psql -U admin -d itom_db -c "\dt"

# ITAM Database
docker exec itsm-postgres psql -U admin -d itam_db -c "\dt"
```

### Check Table Structure

```bash
# Example: Check incidents table structure
docker exec itsm-postgres psql -U admin -d itsm_db -c "\d incidents"
```

## Data Types Reference

| Type | Description | Example |
|------|-------------|---------|
| UUID | Universally unique identifier | 550e8400-e29b-41d4-a716-446655440000 |
| VARCHAR(n) | Variable-length string | 'INCIDENT-001' |
| TEXT | Large text field | 'Long description...' |
| TIMESTAMP | Date and time | 2026-07-14 09:22:00 |
| DATE | Date only | 2026-07-14 |
| DECIMAL(15,2) | Decimal number | 9999.99 |
| BIGINT | Large integer | 9223372036854775807 |

## Constraints and Validation

### Check Constraints

- **Priority**: CRITICAL, HIGH, MEDIUM, LOW
- **Status**: Varies by entity (OPEN, IN_PROGRESS, RESOLVED, CLOSED for incidents)
- **Asset Type**: Predefined values per entity
- **Date Validation**: updated_at >= created_at

### Indexes

All tables include indexes on frequently queried columns:
- Primary key (id)
- Status fields
- Created/updated timestamps
- Owner/assigned fields
- Type/category fields

## Troubleshooting

### Docker Container Not Running

```bash
docker start itsm-postgres
docker ps
```

### Permission Denied

Ensure you have proper Docker permissions:
```bash
# Linux/Mac
sudo docker exec itsm-postgres psql -U admin -l

# Or add user to docker group
sudo usermod -aG docker $USER
```

### Connection Refused

Check if PostgreSQL is listening:
```bash
docker exec itsm-postgres pg_isready -U admin
```

### Script Execution Failed

Check PostgreSQL logs:
```bash
docker logs itsm-postgres
```

### Table Already Exists

The scripts use `CREATE TABLE IF NOT EXISTS`, so re-running is safe:
```bash
# Safe to re-run
./init-databases.sh
```

## Next Steps

1. **Verify Databases**: Run verification commands above
2. **Load Seed Data**: Use `load_seed_data.py` or equivalent
3. **Start Services**: Build and run backend microservices
4. **Test Connections**: Verify services can connect to databases

## Related Scripts

- `load_seed_data.py` - Load sample data into tables
- `load-seed-data.sh` - Bash version of seed data loader
- `load-seed-data.bat` - Windows version of seed data loader

## Support

For issues or questions:
1. Check PostgreSQL logs: `docker logs itsm-postgres`
2. Verify container status: `docker ps`
3. Test connection: `docker exec itsm-postgres psql -U admin -l`
4. Review SQL script: `scripts/init-databases.sql`
