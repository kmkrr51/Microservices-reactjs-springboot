#!/bin/bash

# Seed Data Loader Script
# Loads seed data into all microservices

set -e

echo "=========================================="
echo "Loading Seed Data for All Services"
echo "=========================================="

# Configuration
ITSM_URL="http://localhost:8001/api/v1"
CMDB_URL="http://localhost:8000/api/v1"
ITOM_URL="http://localhost:8002/api/v1"
ITAM_URL="http://localhost:8003/api/v1"
TOKEN="Bearer test-token"  # Replace with actual token after login

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to check if service is running
check_service() {
  local url=$1
  local name=$2
  
  if curl -s "$url/incidents" > /dev/null 2>&1 || curl -s "$url/cis" > /dev/null 2>&1; then
    echo -e "${GREEN}✓${NC} $name is running"
    return 0
  else
    echo -e "${RED}✗${NC} $name is not running on $url"
    return 1
  fi
}

# Function to load ITSM incidents
load_itsm_incidents() {
  echo -e "\n${YELLOW}Loading ITSM Incidents...${NC}"
  
  incidents=(
    '{
      "incidentNumber": "INC-001",
      "title": "Production Database Down",
      "description": "Critical production database is not responding",
      "priority": "CRITICAL",
      "reporter": "admin@example.com"
    }'
    '{
      "incidentNumber": "INC-002",
      "title": "Email Server Slow",
      "description": "Email server responding slowly to requests",
      "priority": "HIGH",
      "reporter": "admin@example.com"
    }'
    '{
      "incidentNumber": "INC-003",
      "title": "VPN Connection Issues",
      "description": "Users unable to connect to corporate VPN",
      "priority": "HIGH",
      "reporter": "ops.manager@example.com"
    }'
    '{
      "incidentNumber": "INC-004",
      "title": "Printer Not Working",
      "description": "Office printer on 3rd floor not responding",
      "priority": "MEDIUM",
      "reporter": "admin@example.com"
    }'
    '{
      "incidentNumber": "INC-005",
      "title": "Network Latency",
      "description": "High network latency observed in main office",
      "priority": "MEDIUM",
      "reporter": "ops.manager@example.com"
    }'
  )
  
  for incident in "${incidents[@]}"; do
    response=$(curl -s -X POST "$ITSM_URL/incidents" \
      -H "Content-Type: application/json" \
      -d "$incident")
    
    incident_number=$(echo "$incident" | grep -o '"incidentNumber":"[^"]*' | cut -d'"' -f4)
    echo -e "${GREEN}✓${NC} Created incident: $incident_number"
  done
}

# Function to load CMDB configuration items
load_cmdb_items() {
  echo -e "\n${YELLOW}Loading CMDB Configuration Items...${NC}"
  
  items=(
    '{
      "name": "Production-DB-01",
      "type": "Database",
      "description": "Primary production database server",
      "owner": "cmdb.admin@example.com"
    }'
    '{
      "name": "Web-Server-01",
      "type": "Server",
      "description": "Primary web application server",
      "owner": "cmdb.admin@example.com"
    }'
    '{
      "name": "Web-Server-02",
      "type": "Server",
      "description": "Secondary web application server",
      "owner": "cmdb.admin@example.com"
    }'
    '{
      "name": "Email-Server",
      "type": "Server",
      "description": "Corporate email server",
      "owner": "cmdb.admin@example.com"
    }'
    '{
      "name": "Backup-Storage",
      "type": "Storage",
      "description": "Backup storage system",
      "owner": "cmdb.admin@example.com"
    }'
    '{
      "name": "Network-Switch-01",
      "type": "Network Device",
      "description": "Core network switch",
      "owner": "ops.manager@example.com"
    }'
    '{
      "name": "Firewall-01",
      "type": "Security Device",
      "description": "Primary firewall",
      "owner": "ops.manager@example.com"
    }'
    '{
      "name": "VPN-Gateway",
      "type": "Network Device",
      "description": "VPN gateway for remote access",
      "owner": "ops.manager@example.com"
    }'
  )
  
  for item in "${items[@]}"; do
    response=$(curl -s -X POST "$CMDB_URL/cis" \
      -H "Content-Type: application/json" \
      -d "$item")
    
    name=$(echo "$item" | grep -o '"name":"[^"]*' | cut -d'"' -f4)
    echo -e "${GREEN}✓${NC} Created CI: $name"
  done
}

# Function to load ITOM assets
load_itom_assets() {
  echo -e "\n${YELLOW}Loading ITOM Discovered Assets...${NC}"
  
  assets=(
    '{
      "name": "Server-01",
      "assetType": "Server",
      "description": "Production web server",
      "ipAddress": "192.168.1.10",
      "hostname": "server-01.example.com"
    }'
    '{
      "name": "Server-02",
      "assetType": "Server",
      "description": "Production application server",
      "ipAddress": "192.168.1.11",
      "hostname": "server-02.example.com"
    }'
    '{
      "name": "Server-03",
      "assetType": "Server",
      "description": "Database server",
      "ipAddress": "192.168.1.12",
      "hostname": "db-server.example.com"
    }'
    '{
      "name": "Workstation-01",
      "assetType": "Workstation",
      "description": "Admin workstation",
      "ipAddress": "192.168.1.100",
      "hostname": "admin-ws.example.com"
    }'
    '{
      "name": "Printer-01",
      "assetType": "Printer",
      "description": "Office printer",
      "ipAddress": "192.168.1.50",
      "hostname": "printer-01.example.com"
    }'
    '{
      "name": "Switch-01",
      "assetType": "Network Switch",
      "description": "Core network switch",
      "ipAddress": "192.168.1.1",
      "hostname": "switch-01.example.com"
    }'
    '{
      "name": "Firewall-01",
      "assetType": "Firewall",
      "description": "Primary firewall",
      "ipAddress": "192.168.1.2",
      "hostname": "firewall-01.example.com"
    }'
    '{
      "name": "Router-01",
      "assetType": "Router",
      "description": "Core router",
      "ipAddress": "10.0.0.1",
      "hostname": "router-01.example.com"
    }'
  )
  
  for asset in "${assets[@]}"; do
    response=$(curl -s -X POST "$ITOM_URL/assets" \
      -H "Content-Type: application/json" \
      -d "$asset")
    
    name=$(echo "$asset" | grep -o '"name":"[^"]*' | cut -d'"' -f4)
    echo -e "${GREEN}✓${NC} Created asset: $name"
  done
}

# Function to load ITAM hardware
load_itam_hardware() {
  echo -e "\n${YELLOW}Loading ITAM Hardware Assets...${NC}"
  
  hardware=(
    '{
      "assetTag": "HW-001",
      "name": "Dell XPS 15",
      "assetType": "Laptop",
      "manufacturer": "Dell",
      "model": "XPS 15",
      "serialNumber": "DELL-XPS-001",
      "purchaseCost": 2500.00
    }'
    '{
      "assetTag": "HW-002",
      "name": "MacBook Pro 16",
      "assetType": "Laptop",
      "manufacturer": "Apple",
      "model": "MacBook Pro 16",
      "serialNumber": "APPLE-MBP-002",
      "purchaseCost": 3500.00
    }'
    '{
      "assetTag": "HW-003",
      "name": "HP ProBook 450",
      "assetType": "Laptop",
      "manufacturer": "HP",
      "model": "ProBook 450",
      "serialNumber": "HP-PB-003",
      "purchaseCost": 1200.00
    }'
    '{
      "assetTag": "HW-004",
      "name": "Lenovo ThinkPad",
      "assetType": "Laptop",
      "manufacturer": "Lenovo",
      "model": "ThinkPad X1",
      "serialNumber": "LENOVO-TP-004",
      "purchaseCost": 1500.00
    }'
    '{
      "assetTag": "HW-005",
      "name": "Dell Monitor 27",
      "assetType": "Monitor",
      "manufacturer": "Dell",
      "model": "U2720Q",
      "serialNumber": "DELL-MON-005",
      "purchaseCost": 450.00
    }'
    '{
      "assetTag": "HW-006",
      "name": "Logitech Keyboard",
      "assetType": "Keyboard",
      "manufacturer": "Logitech",
      "model": "MX Keys",
      "serialNumber": "LOGI-KB-006",
      "purchaseCost": 99.00
    }'
    '{
      "assetTag": "HW-007",
      "name": "Logitech Mouse",
      "assetType": "Mouse",
      "manufacturer": "Logitech",
      "model": "MX Master 3",
      "serialNumber": "LOGI-MS-007",
      "purchaseCost": 99.00
    }'
    '{
      "assetTag": "HW-008",
      "name": "Cisco IP Phone",
      "assetType": "Phone",
      "manufacturer": "Cisco",
      "model": "8861",
      "serialNumber": "CISCO-PH-008",
      "purchaseCost": 350.00
    }'
    '{
      "assetTag": "HW-009",
      "name": "Apple iPad Pro",
      "assetType": "Tablet",
      "manufacturer": "Apple",
      "model": "iPad Pro 12.9",
      "serialNumber": "APPLE-IPD-009",
      "purchaseCost": 1200.00
    }'
    '{
      "assetTag": "HW-010",
      "name": "Samsung 4K Monitor",
      "assetType": "Monitor",
      "manufacturer": "Samsung",
      "model": "LU28E590DS",
      "serialNumber": "SAMSUNG-MON-010",
      "purchaseCost": 350.00
    }'
  )
  
  for item in "${hardware[@]}"; do
    response=$(curl -s -X POST "$ITAM_URL/assets" \
      -H "Content-Type: application/json" \
      -d "$item")
    
    name=$(echo "$item" | grep -o '"name":"[^"]*' | cut -d'"' -f4)
    echo -e "${GREEN}✓${NC} Created hardware: $name"
  done
}

# Main execution
echo "Checking services..."
check_service "$ITSM_URL" "ITSM Service" || exit 1
check_service "$CMDB_URL" "CMDB Service" || exit 1
check_service "$ITOM_URL" "ITOM Service" || exit 1
check_service "$ITAM_URL" "ITAM Service" || exit 1

# Load seed data
load_itsm_incidents
load_cmdb_items
load_itom_assets
load_itam_hardware

echo -e "\n${GREEN}=========================================="
echo "✓ Seed Data Loaded Successfully!"
echo "==========================================${NC}"
echo ""
echo "Summary:"
echo "  - ITSM Incidents: 5"
echo "  - CMDB Items: 8"
echo "  - ITOM Assets: 8"
echo "  - ITAM Hardware: 10"
echo "  - Total Records: 31"
echo ""
echo "You can now login with:"
echo "  Email: admin@example.com"
echo "  Password: Admin@123456"
