#!/usr/bin/env python3

"""
Snowrepo Seed Data Loader Script (SQL)
Loads sample data into all microservice databases via SQL script
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
SEED_SCRIPT_PATH = os.path.join(SCRIPT_DIR, "seed-data.sql")

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

def check_databases():
  """Check if databases exist"""
  print_info("Checking databases...")
  
  databases = ["itsm_db", "cmdb_db", "itom_db", "itam_db"]
  missing = []
  
  for db in databases:
    returncode, stdout, _ = run_command(
      f"docker exec {CONTAINER_NAME} psql -U {DB_USER} -lqt | grep {db}",
      check=False
    )
    if returncode != 0:
      missing.append(db)
  
  if missing:
    print_error(f"Missing databases: {', '.join(missing)}")
    print_info("Please run init-databases script first.")
    return False
  
  print_success("All databases exist")
  return True

def load_seed_data():
  """Load seed data using the SQL script"""
  print_info(f"Loading seed data...")
  print_info(f"Script: {SEED_SCRIPT_PATH}")
  print()
  
  # Read the SQL script
  try:
    with open(SEED_SCRIPT_PATH, 'r') as f:
      sql_script = f.read()
  except FileNotFoundError:
    print_error(f"Seed data script not found: {SEED_SCRIPT_PATH}")
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
      print_error("Seed data loading failed!")
      if result.stderr:
        print(f"\n{Colors.FAIL}{result.stderr}{Colors.ENDC}")
      return False
    
    if result.stdout:
      print(result.stdout)
    
    return True
  
  except Exception as e:
    print_error(f"Failed to load seed data: {str(e)}")
    return False

def verify_data():
  """Verify that seed data was loaded"""
  print_info("Verifying seed data...")
  
  databases = {
    "itsm_db": ("incidents", 8),
    "cmdb_db": ("configuration_items", 10),
    "itom_db": ("discovered_assets", 8),
    "itam_db": ("hardware_assets", 10),
  }
  
  all_ok = True
  total_records = 0
  
  for db, (table, expected_count) in databases.items():
    cmd = f"docker exec {CONTAINER_NAME} psql -U {DB_USER} -d {db} -t -c \"SELECT COUNT(*) FROM {table}\""
    returncode, stdout, _ = run_command(cmd, check=False)
    
    if returncode == 0:
      count = int(stdout.strip())
      total_records += count
      status = "✓" if count >= expected_count else "✗"
      print(f"  {status} {db}.{table}: {count} records (expected {expected_count})")
      if count < expected_count:
        all_ok = False
    else:
      print(f"  ✗ {db}.{table}: Error checking count")
      all_ok = False
  
  print()
  print(f"Total records loaded: {total_records}")
  
  return all_ok

def main():
  """Main function"""
  print_header("Snowrepo Seed Data Loader")
  
  # Check Docker
  if not check_docker():
    sys.exit(1)
  
  # Check container
  if not check_container():
    sys.exit(1)
  
  # Check databases
  if not check_databases():
    sys.exit(1)
  
  # Load seed data
  if not load_seed_data():
    sys.exit(1)
  
  # Verify data
  print()
  if not verify_data():
    print_warning("Some records may not have been loaded correctly")
  
  # Print success message
  print()
  print_success("Seed data loaded successfully!")
  print()
  print_header("Seed Data Summary")
  
  print(f"{Colors.BOLD}Databases Populated:{Colors.ENDC}")
  print("  - itsm_db: 8 incidents")
  print("  - cmdb_db: 10 configuration items")
  print("  - itom_db: 8 discovered assets")
  print("  - itam_db: 10 hardware assets")
  print()
  
  print(f"{Colors.BOLD}Test Users:{Colors.ENDC}")
  print("  - admin@example.com (Admin)")
  print("  - itsm.manager@example.com (ITSM Manager)")
  print("  - cmdb.admin@example.com (CMDB Admin)")
  print("  - ops.manager@example.com (Operations Manager)")
  print()
  
  print(f"{Colors.BOLD}Next Steps:{Colors.ENDC}")
  print("  1. Build backend microservices")
  print("  2. Start backend services")
  print("  3. Start frontend application")
  print("  4. Login with test credentials")
  print()

if __name__ == "__main__":
  main()
