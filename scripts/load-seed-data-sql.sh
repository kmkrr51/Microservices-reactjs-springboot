#!/bin/bash

# ============================================================================
# Snowrepo Seed Data Loader Script (Linux/Mac)
# Loads sample data into all microservice databases
# ============================================================================

set -e

# Configuration
CONTAINER_NAME="itsm-postgres"
DB_USER="admin"
DB_PASSWORD="secret"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SEED_SCRIPT_PATH="$SCRIPT_DIR/seed-data.sql"

echo ""
echo "============================================================================"
echo "Snowrepo Seed Data Loader"
echo "============================================================================"
echo ""

# Check if Docker is running
echo "[INFO] Checking Docker status..."
if ! docker ps > /dev/null 2>&1; then
  echo "[ERROR] Docker is not running. Please start Docker and try again."
  exit 1
fi

# Check if PostgreSQL container exists
echo "[INFO] Checking PostgreSQL container..."
if ! docker ps -a --filter "name=$CONTAINER_NAME" --format "{{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
  echo "[ERROR] PostgreSQL container '$CONTAINER_NAME' not found."
  exit 1
fi

# Check if container is running
if ! docker ps --filter "name=$CONTAINER_NAME" --format "{{.Names}}" | grep -q "^$CONTAINER_NAME$"; then
  echo "[WARNING] Container is not running. Starting it..."
  docker start "$CONTAINER_NAME"
  sleep 3
fi

# Check if databases exist
echo "[INFO] Checking databases..."
if ! docker exec "$CONTAINER_NAME" psql -U "$DB_USER" -lqt | grep -q "itsm_db"; then
  echo "[ERROR] Databases not found. Please run init-databases script first."
  exit 1
fi

# Execute seed data script
echo "[INFO] Loading seed data..."
echo "[INFO] Script: $SEED_SCRIPT_PATH"
echo ""

cat "$SEED_SCRIPT_PATH" | docker exec -i "$CONTAINER_NAME" psql -U "$DB_USER"

if [ $? -ne 0 ]; then
  echo ""
  echo "[ERROR] Seed data loading failed!"
  exit 1
fi

# Verify data
echo ""
echo "[INFO] Verifying seed data..."
echo ""

ITSM_COUNT=$(docker exec "$CONTAINER_NAME" psql -U "$DB_USER" -d itsm_db -t -c "SELECT COUNT(*) FROM incidents" | xargs)
CMDB_COUNT=$(docker exec "$CONTAINER_NAME" psql -U "$DB_USER" -d cmdb_db -t -c "SELECT COUNT(*) FROM configuration_items" | xargs)
ITOM_COUNT=$(docker exec "$CONTAINER_NAME" psql -U "$DB_USER" -d itom_db -t -c "SELECT COUNT(*) FROM discovered_assets" | xargs)
ITAM_COUNT=$(docker exec "$CONTAINER_NAME" psql -U "$DB_USER" -d itam_db -t -c "SELECT COUNT(*) FROM hardware_assets" | xargs)

TOTAL=$((ITSM_COUNT + CMDB_COUNT + ITOM_COUNT + ITAM_COUNT))

echo "[SUCCESS] Seed data loaded successfully!"
echo ""
echo "============================================================================"
echo "Seed Data Summary"
echo "============================================================================"
echo ""
echo "Databases Populated:"
echo "  - itsm_db.incidents: $ITSM_COUNT records"
echo "  - cmdb_db.configuration_items: $CMDB_COUNT records"
echo "  - itom_db.discovered_assets: $ITOM_COUNT records"
echo "  - itam_db.hardware_assets: $ITAM_COUNT records"
echo ""
echo "Total Records: $TOTAL"
echo ""
echo "Test Users:"
echo "  - admin@example.com (Admin)"
echo "  - itsm.manager@example.com (ITSM Manager)"
echo "  - cmdb.admin@example.com (CMDB Admin)"
echo "  - ops.manager@example.com (Operations Manager)"
echo ""
echo "Next Steps:"
echo "  1. Build backend microservices"
echo "  2. Start backend services"
echo "  3. Start frontend application"
echo "  4. Login with test credentials"
echo ""
echo "============================================================================"
echo ""

exit 0
