#!/usr/bin/env python3

"""
Snowrepo Database Initialization Script (Python)
Initializes all databases and tables in PostgreSQL Docker container
"""

import subprocess
import sys
import os
import time

# Configuration
CONTAINER_NAME = "itsm-postgres"
DB_USER = "admin"
DB_PASSWORD = "secret"
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
SCRIPT_PATH = os.path.join(SCRIPT_DIR, "init-databases.sql")

# Colors for output
class Colors:
  HEADER = '\033[95m'
  OKBLUE = '\033[94m'
  OKCYAN = '\033[96m'
  OKGREEN = '\033[92m'
  WARNING = '\033[93m'
  FAIL = '\033[91m'
  ENDC = '\033[0m'
  BOLD = '\033[1m'
  UNDERLINE = '\033[4m'

def print_header(text):
  print(f"\n{Colors.BOLD}{Colors.OKBLUE}{'=' * 76}{Colors.ENDC}")
  print(f"{Colors.BOLD}{Colors.OKBLUE}{text}{Colors.ENDC}")
  print(f"{Colors.BOLD}{Colors.OKBLUE}{'=' * 76}{Colors.ENDC}\n")

def print_info(text):
  print(f"{Colors.OKCYAN}[INFO]{Colors.ENDC} {text}")

def print_success(text):
  print(f"{Colors.OKGREEN}[SUCCESS]{Colors.ENDC} {text}")

def print_warning(text):
  print(f"{Colors.WARNING}[WARNING]{Colors.ENDC} {text}")

def print_error(text):
  print(f"{Colors.FAIL}[ERROR]{Colors.ENDC} {text}")

def run_command(cmd, check=True):
  """Run a shell command and return the result"""
  try:
    result = subprocess.run(cmd, shell=True, capture_output=True, text=True, check=check)
    return result.returncode, result.stdout, result.stderr
  except subprocess.CalledProcessError as e:
    return e.returncode, e.stdout, e.stderr

def check_docker():
  """Check if Docker is running"""
  print_info("Checking Docker status...")
  returncode, _, _ = run_command("docker ps", check=False)
  if returncode != 0:
    print_error("Docker is not running. Please start Docker and try again.")
    return False
  return True

def check_container():
  """Check if PostgreSQL container exists and is running"""
  print_info("Checking PostgreSQL container...")
  
  # Check if container exists
  returncode, stdout, _ = run_command(
    f"docker ps -a --filter \"name={CONTAINER_NAME}\" --format \"{{{{.Names}}}}\"",
    check=False
  )
  
  if CONTAINER_NAME not in stdout:
    print_error(f"PostgreSQL container '{CONTAINER_NAME}' not found.")
    return False
  
  # Check if container is running
  returncode, stdout, _ = run_command(
    f"docker ps --filter \"name={CONTAINER_NAME}\" --format \"{{{{.Names}}}}\"",
    check=False
  )
  
  if CONTAINER_NAME not in stdout:
    print_warning("Container is not running. Starting it...")
    run_command(f"docker start {CONTAINER_NAME}")
    time.sleep(3)
  
  return True

def init_databases():
  """Initialize databases using the SQL script"""
  print_info(f"Running database initialization script...")
  print_info(f"Script: {SCRIPT_PATH}")
  print()
  
  # Read the SQL script
  try:
    with open(SCRIPT_PATH, 'r') as f:
      sql_script = f.read()
  except FileNotFoundError:
    print_error(f"SQL script not found: {SCRIPT_PATH}")
    return False
  
  # Execute the script in the container
  cmd = f"docker exec -i {CONTAINER_NAME} psql -U {DB_USER}"
  
  try:
    result = subprocess.run(
      cmd,
      input=sql_script,
      shell=True,
      capture_output=True,
      text=True,
      check=False
    )
    
    if result.returncode != 0:
      print_error("Database initialization failed!")
      if result.stderr:
        print(f"\n{Colors.FAIL}{result.stderr}{Colors.ENDC}")
      return False
    
    if result.stdout:
      print(result.stdout)
    
    return True
  
  except Exception as e:
    print_error(f"Failed to execute initialization: {str(e)}")
    return False

def main():
  """Main function"""
  print_header("Snowrepo Database Initialization")
  
  # Check Docker
  if not check_docker():
    sys.exit(1)
  
  # Check container
  if not check_container():
    sys.exit(1)
  
  # Initialize databases
  if not init_databases():
    sys.exit(1)
  
  # Print success message
  print()
  print_success("Database initialization completed successfully!")
  print()
  print_header("Initialization Summary")
  
  print(f"{Colors.BOLD}Databases Created:{Colors.ENDC}")
  print("  - itsm_db (Incident Management)")
  print("  - cmdb_db (Configuration Management)")
  print("  - itom_db (IT Operations Management)")
  print("  - itam_db (IT Asset Management)")
  print()
  
  print(f"{Colors.BOLD}Tables Created:{Colors.ENDC}")
  print("  - itsm_db.incidents")
  print("  - cmdb_db.configuration_items")
  print("  - itom_db.discovered_assets")
  print("  - itam_db.hardware_assets")
  print()
  
  print_header("Ready for Seed Data")
  print("All databases and tables are ready for seed data insertion.")
  print()

if __name__ == "__main__":
  main()
