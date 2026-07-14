#!/bin/bash

# ============================================================================
# Snowrepo Services Stop Script (Linux/Mac)
# Stops all backend microservices and frontend application
# ============================================================================

echo ""
echo "============================================================================"
echo "Snowrepo Services Stop"
echo "============================================================================"
echo ""

# Stop backend services
echo "[INFO] Stopping backend services..."
echo ""

# Kill processes on specific ports
kill_process_on_port() {
  local port=$1
  local service=$2
  
  echo "[INFO] Stopping $service (port $port)..."
  
  # Find and kill process on port
  local pid=$(lsof -ti:$port 2>/dev/null)
  
  if [ -n "$pid" ]; then
    kill -9 $pid 2>/dev/null
    echo "[OK] Killed process on port $port"
  else
    echo "[INFO] No process running on port $port"
  fi
}

# Stop all services
kill_process_on_port 8000 "API Gateway"
kill_process_on_port 8001 "CMDB Service"
kill_process_on_port 8002 "ITSM Service"
kill_process_on_port 8003 "ITOM Service"
kill_process_on_port 8004 "ITAM Service"
kill_process_on_port 5173 "Frontend"

echo ""
echo "[SUCCESS] All services stopped!"
echo ""
echo "============================================================================"
echo "Service Status"
echo "============================================================================"
echo ""

# Check port availability
check_port() {
  local port=$1
  local service=$2
  
  if lsof -Pi :$port -sTCP:LISTEN -t >/dev/null 2>&1; then
    echo "[WARNING] Port $port ($service): IN USE"
  else
    echo "[OK] Port $port ($service): FREE"
  fi
}

echo "[INFO] Checking port availability..."
echo ""

check_port 8000 "API Gateway"
check_port 8001 "CMDB"
check_port 8002 "ITSM"
check_port 8003 "ITOM"
check_port 8004 "ITAM"
check_port 5173 "Frontend"

echo ""
echo "============================================================================"
echo "Docker Services (Still Running)"
echo "============================================================================"
echo ""
echo "The following Docker services are still running:"
echo "  - PostgreSQL (5432)"
echo "  - Kafka (9092)"
echo "  - Zookeeper (2181)"
echo "  - Keycloak (8080)"
echo ""
echo "To stop Docker services, run:"
echo "  docker stop [container-name]"
echo "  or"
echo "  docker-compose down"
echo ""
echo "============================================================================"
echo ""

exit 0
