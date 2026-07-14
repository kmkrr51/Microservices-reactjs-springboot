#!/usr/bin/env python3
"""
Seed Data Loader Script
Loads seed data into all microservices
"""

import requests
import json
import sys
from typing import List, Dict, Any

# Configuration
SERVICES = {
    "itsm": "http://localhost:8001/api/v1",
    "cmdb": "http://localhost:8000/api/v1",
    "itom": "http://localhost:8002/api/v1",
    "itam": "http://localhost:8003/api/v1",
}

# ANSI color codes
GREEN = "\033[92m"
RED = "\033[91m"
YELLOW = "\033[93m"
RESET = "\033[0m"

def print_success(message: str) -> None:
    """Print success message"""
    print(f"{GREEN}✓{RESET} {message}")

def print_error(message: str) -> None:
    """Print error message"""
    print(f"{RED}✗{RESET} {message}")

def print_info(message: str) -> None:
    """Print info message"""
    print(f"{YELLOW}→{RESET} {message}")

def check_service(url: str, service_name: str) -> bool:
    """Check if service is running"""
    try:
        response = requests.get(f"{url}/incidents", timeout=2)
        if response.status_code in [200, 404]:
            print_success(f"{service_name} is running")
            return True
    except requests.exceptions.RequestException:
        pass
    
    print_error(f"{service_name} is not running on {url}")
    return False

def load_itsm_incidents() -> None:
    """Load ITSM incidents"""
    print_info("Loading ITSM Incidents...")
    
    incidents: List[Dict[str, Any]] = [
        {
            "incidentNumber": "INC-001",
            "title": "Production Database Down",
            "description": "Critical production database is not responding",
            "priority": "CRITICAL",
            "reporter": "admin@example.com"
        },
        {
            "incidentNumber": "INC-002",
            "title": "Email Server Slow",
            "description": "Email server responding slowly to requests",
            "priority": "HIGH",
            "reporter": "admin@example.com"
        },
        {
            "incidentNumber": "INC-003",
            "title": "VPN Connection Issues",
            "description": "Users unable to connect to corporate VPN",
            "priority": "HIGH",
            "reporter": "ops.manager@example.com"
        },
        {
            "incidentNumber": "INC-004",
            "title": "Printer Not Working",
            "description": "Office printer on 3rd floor not responding",
            "priority": "MEDIUM",
            "reporter": "admin@example.com"
        },
        {
            "incidentNumber": "INC-005",
            "title": "Network Latency",
            "description": "High network latency observed in main office",
            "priority": "MEDIUM",
            "reporter": "ops.manager@example.com"
        }
    ]
    
    for incident in incidents:
        try:
            response = requests.post(
                f"{SERVICES['itsm']}/incidents",
                json=incident,
                headers={"Content-Type": "application/json"},
                timeout=5
            )
            if response.status_code in [200, 201]:
                print_success(f"Created incident: {incident['incidentNumber']}")
            else:
                print_error(f"Failed to create incident: {incident['incidentNumber']}")
        except requests.exceptions.RequestException as e:
            print_error(f"Error creating incident: {str(e)}")

def load_cmdb_items() -> None:
    """Load CMDB configuration items"""
    print_info("Loading CMDB Configuration Items...")
    
    items: List[Dict[str, Any]] = [
        {
            "name": "Production-DB-01",
            "type": "Database",
            "description": "Primary production database server",
            "owner": "cmdb.admin@example.com"
        },
        {
            "name": "Web-Server-01",
            "type": "Server",
            "description": "Primary web application server",
            "owner": "cmdb.admin@example.com"
        },
        {
            "name": "Web-Server-02",
            "type": "Server",
            "description": "Secondary web application server",
            "owner": "cmdb.admin@example.com"
        },
        {
            "name": "Email-Server",
            "type": "Server",
            "description": "Corporate email server",
            "owner": "cmdb.admin@example.com"
        },
        {
            "name": "Backup-Storage",
            "type": "Storage",
            "description": "Backup storage system",
            "owner": "cmdb.admin@example.com"
        },
        {
            "name": "Network-Switch-01",
            "type": "Network Device",
            "description": "Core network switch",
            "owner": "ops.manager@example.com"
        },
        {
            "name": "Firewall-01",
            "type": "Security Device",
            "description": "Primary firewall",
            "owner": "ops.manager@example.com"
        },
        {
            "name": "VPN-Gateway",
            "type": "Network Device",
            "description": "VPN gateway for remote access",
            "owner": "ops.manager@example.com"
        }
    ]
    
    for item in items:
        try:
            response = requests.post(
                f"{SERVICES['cmdb']}/cis",
                json=item,
                headers={"Content-Type": "application/json"},
                timeout=5
            )
            if response.status_code in [200, 201]:
                print_success(f"Created CI: {item['name']}")
            else:
                print_error(f"Failed to create CI: {item['name']}")
        except requests.exceptions.RequestException as e:
            print_error(f"Error creating CI: {str(e)}")

def load_itom_assets() -> None:
    """Load ITOM discovered assets"""
    print_info("Loading ITOM Discovered Assets...")
    
    assets: List[Dict[str, Any]] = [
        {
            "name": "Server-01",
            "assetType": "Server",
            "description": "Production web server",
            "ipAddress": "192.168.1.10",
            "hostname": "server-01.example.com"
        },
        {
            "name": "Server-02",
            "assetType": "Server",
            "description": "Production application server",
            "ipAddress": "192.168.1.11",
            "hostname": "server-02.example.com"
        },
        {
            "name": "Server-03",
            "assetType": "Server",
            "description": "Database server",
            "ipAddress": "192.168.1.12",
            "hostname": "db-server.example.com"
        },
        {
            "name": "Workstation-01",
            "assetType": "Workstation",
            "description": "Admin workstation",
            "ipAddress": "192.168.1.100",
            "hostname": "admin-ws.example.com"
        },
        {
            "name": "Printer-01",
            "assetType": "Printer",
            "description": "Office printer",
            "ipAddress": "192.168.1.50",
            "hostname": "printer-01.example.com"
        },
        {
            "name": "Switch-01",
            "assetType": "Network Switch",
            "description": "Core network switch",
            "ipAddress": "192.168.1.1",
            "hostname": "switch-01.example.com"
        },
        {
            "name": "Firewall-01",
            "assetType": "Firewall",
            "description": "Primary firewall",
            "ipAddress": "192.168.1.2",
            "hostname": "firewall-01.example.com"
        },
        {
            "name": "Router-01",
            "assetType": "Router",
            "description": "Core router",
            "ipAddress": "10.0.0.1",
            "hostname": "router-01.example.com"
        }
    ]
    
    for asset in assets:
        try:
            response = requests.post(
                f"{SERVICES['itom']}/assets",
                json=asset,
                headers={"Content-Type": "application/json"},
                timeout=5
            )
            if response.status_code in [200, 201]:
                print_success(f"Created asset: {asset['name']}")
            else:
                print_error(f"Failed to create asset: {asset['name']}")
        except requests.exceptions.RequestException as e:
            print_error(f"Error creating asset: {str(e)}")

def load_itam_hardware() -> None:
    """Load ITAM hardware assets"""
    print_info("Loading ITAM Hardware Assets...")
    
    hardware: List[Dict[str, Any]] = [
        {
            "assetTag": "HW-001",
            "name": "Dell XPS 15",
            "assetType": "Laptop",
            "manufacturer": "Dell",
            "model": "XPS 15",
            "serialNumber": "DELL-XPS-001",
            "purchaseCost": 2500.00
        },
        {
            "assetTag": "HW-002",
            "name": "MacBook Pro 16",
            "assetType": "Laptop",
            "manufacturer": "Apple",
            "model": "MacBook Pro 16",
            "serialNumber": "APPLE-MBP-002",
            "purchaseCost": 3500.00
        },
        {
            "assetTag": "HW-003",
            "name": "HP ProBook 450",
            "assetType": "Laptop",
            "manufacturer": "HP",
            "model": "ProBook 450",
            "serialNumber": "HP-PB-003",
            "purchaseCost": 1200.00
        },
        {
            "assetTag": "HW-004",
            "name": "Lenovo ThinkPad",
            "assetType": "Laptop",
            "manufacturer": "Lenovo",
            "model": "ThinkPad X1",
            "serialNumber": "LENOVO-TP-004",
            "purchaseCost": 1500.00
        },
        {
            "assetTag": "HW-005",
            "name": "Dell Monitor 27",
            "assetType": "Monitor",
            "manufacturer": "Dell",
            "model": "U2720Q",
            "serialNumber": "DELL-MON-005",
            "purchaseCost": 450.00
        },
        {
            "assetTag": "HW-006",
            "name": "Logitech Keyboard",
            "assetType": "Keyboard",
            "manufacturer": "Logitech",
            "model": "MX Keys",
            "serialNumber": "LOGI-KB-006",
            "purchaseCost": 99.00
        },
        {
            "assetTag": "HW-007",
            "name": "Logitech Mouse",
            "assetType": "Mouse",
            "manufacturer": "Logitech",
            "model": "MX Master 3",
            "serialNumber": "LOGI-MS-007",
            "purchaseCost": 99.00
        },
        {
            "assetTag": "HW-008",
            "name": "Cisco IP Phone",
            "assetType": "Phone",
            "manufacturer": "Cisco",
            "model": "8861",
            "serialNumber": "CISCO-PH-008",
            "purchaseCost": 350.00
        },
        {
            "assetTag": "HW-009",
            "name": "Apple iPad Pro",
            "assetType": "Tablet",
            "manufacturer": "Apple",
            "model": "iPad Pro 12.9",
            "serialNumber": "APPLE-IPD-009",
            "purchaseCost": 1200.00
        },
        {
            "assetTag": "HW-010",
            "name": "Samsung 4K Monitor",
            "assetType": "Monitor",
            "manufacturer": "Samsung",
            "model": "LU28E590DS",
            "serialNumber": "SAMSUNG-MON-010",
            "purchaseCost": 350.00
        }
    ]
    
    for item in hardware:
        try:
            response = requests.post(
                f"{SERVICES['itam']}/assets",
                json=item,
                headers={"Content-Type": "application/json"},
                timeout=5
            )
            if response.status_code in [200, 201]:
                print_success(f"Created hardware: {item['assetTag']}")
            else:
                print_error(f"Failed to create hardware: {item['assetTag']}")
        except requests.exceptions.RequestException as e:
            print_error(f"Error creating hardware: {str(e)}")

def main() -> None:
    """Main function"""
    print()
    print("=" * 50)
    print("Loading Seed Data for All Services")
    print("=" * 50)
    print()
    
    # Check services
    print("Checking services...")
    all_running = True
    for service_name, url in SERVICES.items():
        if not check_service(url, service_name.upper()):
            all_running = False
    
    if not all_running:
        print_error("Some services are not running. Please start them first.")
        sys.exit(1)
    
    print()
    
    # Load seed data
    load_itsm_incidents()
    print()
    load_cmdb_items()
    print()
    load_itom_assets()
    print()
    load_itam_hardware()
    
    print()
    print("=" * 50)
    print_success("Seed Data Loaded Successfully!")
    print("=" * 50)
    print()
    print("Summary:")
    print("  - ITSM Incidents: 5")
    print("  - CMDB Items: 8")
    print("  - ITOM Assets: 8")
    print("  - ITAM Hardware: 10")
    print("  - Total Records: 31")
    print()
    print("You can now login with:")
    print("  Email: admin@example.com")
    print("  Password: Admin@123456")
    print()

if __name__ == "__main__":
    main()
