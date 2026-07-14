# Build Backend Services

**Status**: ⏳ REQUIRES MAVEN

---

## Prerequisites

Maven 3.8+ must be installed and in PATH.

### Check Maven Installation
```bash
mvn -version
```

### Install Maven (if not installed)

**Windows**:
1. Download: https://maven.apache.org/download.cgi
2. Extract to: `C:\Program Files\apache-maven-3.9.x`
3. Add to PATH:
   - System Properties → Environment Variables
   - New variable: `MAVEN_HOME = C:\Program Files\apache-maven-3.9.x`
   - Edit PATH: Add `%MAVEN_HOME%\bin`
4. Restart terminal and verify: `mvn -version`

**macOS** (Homebrew):
```bash
brew install maven
mvn -version
```

**Linux** (Ubuntu):
```bash
sudo apt-get install maven
mvn -version
```

---

## Build All Services

From project root:

```bash
mvn clean package -DskipTests
```

**Expected output**:
```
[INFO] Building Snowrepo Parent 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building shared-domain 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building shared-exceptions 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building shared-utils 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building shared-logging 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building api-gateway 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building cmdb 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building itsm 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building itom 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] Building itam 1.0.0-SNAPSHOT
[INFO] ────────────────────────────────────────
[INFO] BUILD SUCCESS
```

**Build time**: ~5-10 minutes

---

## Build Individual Services

```bash
# CMDB
mvn clean package -DskipTests -pl cmdb

# ITSM
mvn clean package -DskipTests -pl itsm

# ITOM
mvn clean package -DskipTests -pl itom

# ITAM
mvn clean package -DskipTests -pl itam
```

---

## Verify Build

Check for JAR files:

```bash
ls -la cmdb/target/cmdb-1.0.0-SNAPSHOT.jar
ls -la itsm/target/itsm-1.0.0-SNAPSHOT.jar
ls -la itom/target/itom-1.0.0-SNAPSHOT.jar
ls -la itam/target/itam-1.0.0-SNAPSHOT.jar
```

All should exist and be > 50MB.

---

## Next Steps

After successful build:

1. Start backend services
2. Run seed data loader
3. Start frontend
4. Login and test

---

**Status**: ⏳ AWAITING MAVEN INSTALLATION
