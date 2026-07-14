#!/bin/bash

# ============================================================================
# Snowrepo Database Initialization Script (Linux/Mac)
# Initializes all databases and tables in PostgreSQL Docker container
# ============================================================================

set -e

# Configuration
CONTAINER_NAME="itsm-postgres"
DB_USER="admin"
DB_PASSWORD="secret"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SCRIPT_PATH="$SCRIPT_DIR/init-databases.sql"

echo ""
echo "============================================================================"
echo "Snowrepo Database Initialization"
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

# Execute initialization script
echo "[INFO] Running database initialization script..."
echo "[INFO] Script: $SCRIPT_PATH"
echo ""

cat "$SCRIPT_PATH" | docker exec -i "$CONTAINER_NAME" psql -U "$DB_USER"

if [ $? -ne 0 ]; then
  echo ""
  echo "[ERROR] Database initialization failed!"
  exit 1
fi

echo ""
echo "[SUCCESS] Database initialization completed successfully!"
echo ""
echo "============================================================================"
echo "Databases Created:"
echo "  - itsm_db (Incident Management)"
echo "  - cmdb_db (Configuration Management)"
echo "  - itom_db (IT Operations Management)"
echo "  - itam_db (IT Asset Management)"
echo "============================================================================"
echo ""
echo "Tables Created:"
echo "  - itsm_db.incidents"
echo "  - cmdb_db.configuration_items"
echo "  - itom_db.discovered_assets"
echo "  - itam_db.hardware_assets"
echo "============================================================================"
echo ""

exit 0
