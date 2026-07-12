# Development Quick Start Guide
## Get Started in 5 Minutes

**Status**: Ready for Development  
**Last Updated**: July 12, 2026

---

## 5-Minute Quick Start

### Step 1: Clone Repository (1 minute)
```bash
git clone https://github.com/your-org/snowrepo-springboot.git
cd snowrepo-springboot
```

### Step 2: Verify Prerequisites (1 minute)
```bash
# Check Java 21
java -version  # Should show Java 21

# Check Maven
mvn -version   # Should show Maven 3.9+

# Check Git
git --version  # Should show Git 2.40+

# Check Docker
docker --version  # Should show Docker 24.0+
```

### Step 3: Start Docker Services (1 minute)
```bash
# Start databases and services
docker-compose up -d

# Verify services
docker-compose ps
```

### Step 4: Build Project (1 minute)
```bash
# Build entire project
mvn clean install

# Should see: BUILD SUCCESS
```

### Step 5: Run Tests (1 minute)
```bash
# Run all tests
mvn test

# Should see: BUILD SUCCESS
```

---

## You're Ready to Develop! 🚀

**Next Steps**:
1. Open project in IDE (IntelliJ IDEA or VS Code)
2. Create feature branch: `git checkout -b feature/your-feature`
3. Start coding!
4. Follow commit format: `[scope] message`
5. Push and create pull request

---

## Common Commands

### Build & Test
```bash
mvn clean install              # Build everything
mvn test                       # Run tests
mvn clean test jacoco:report   # Run tests with coverage
mvn clean verify sonar:sonar   # Run SonarQube analysis
```

### Git Workflow
```bash
git status                     # Check status
git add .                      # Stage changes
git commit -m "[scope] msg"    # Commit
git push origin feature/name   # Push
```

### Docker
```bash
docker-compose up -d           # Start services
docker-compose down            # Stop services
docker-compose ps              # List services
docker-compose logs -f         # View logs
```

---

## Troubleshooting

**Build fails?**
```bash
rm -rf ~/.m2/repository
mvn clean install
```

**Docker won't start?**
```bash
docker-compose down -v
docker-compose up -d
```

**Tests failing?**
```bash
mvn clean test -X  # Run with debug output
```

---

## Need Help?

- **Slack**: #development
- **Wiki**: https://github.com/your-org/snowrepo-springboot/wiki
- **Issues**: https://github.com/your-org/snowrepo-springboot/issues

---

**Happy Coding!** 💻
