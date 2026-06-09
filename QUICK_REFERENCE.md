# Quick Reference Guide

## Project Structure Summary

```
project/
├── pom.xml                    # Maven configuration
├── build.bat                  # Build script (run this first)
├── run.bat                    # Run script (after building)
├── README.md                  # Full documentation
├── MAVEN_SETUP.md            # Maven installation guide
├── QUICK_REFERENCE.md        # This file
└── src/
    ├── main/
    │   ├── java/com/example/
    │   │   ├── Application.java
    │   │   ├── config/SparkConfig.java
    │   │   ├── controller/DeltaController.java
    │   │   ├── service/DeltaService.java
    │   │   ├── service/DeltaSharingService.java
    │   │   └── error/GlobalExceptionHandler.java
    │   └── resources/application.yml
    └── test/
```

## Quick Start (3 Steps)

### 1. Install Maven (if not already installed)
   - See `MAVEN_SETUP.md` for detailed instructions
   - Or: `winget install Apache.Maven` (Windows 11+)

### 2. Build the Project
   ```bash
   .\build.bat
   ```
   OR manually:
   ```bash
   mvn clean package -DskipTests
   ```

### 3. Run the Application
   ```bash
   .\run.bat
   ```
   OR manually:
   ```bash
   mvn spring-boot:run
   ```
   OR after building:
   ```bash
   java -jar target/spark-delta-sharing-app-1.0.0.jar
   ```

The app will start on: **http://localhost:8080**

---

## API Testing with cURL

### Health Check
```bash
curl http://localhost:8080/api/health
```

Response:
```json
{
  "status": "UP",
  "message": "Spark Delta Sharing App is running"
}
```

### List Delta Tables
```bash
curl http://localhost:8080/api/delta/tables
```

### Read a Specific Delta Table
```bash
curl http://localhost:8080/api/delta/table/users
```

### Delta Sharing Profile
```bash
curl http://localhost:8080/api/sharing/profile
```

### List Shared Tables
```bash
curl http://localhost:8080/api/sharing/tables
```

---

## Testing with Sample Data

### Create a Sample Delta Table

Using Spark Shell:
```bash
spark-shell --packages io.delta:delta-core_2.12:3.1.0
```

In the shell (copy-paste):
```scala
val data = Seq(
  (1, "Alice", 25),
  (2, "Bob", 30),
  (3, "Charlie", 35)
).toDF("id", "name", "age")

data.write.format("delta").mode("overwrite").save("file:///tmp/delta/users")
:quit
```

Then query it:
```bash
curl http://localhost:8080/api/delta/table/users
```

---

## Configuration

Edit `src/main/resources/application.yml` to customize:

**Change Port:**
```yaml
server:
  port: 9090  # Change from 8080 to 9090
```

**Change Delta Lake Path:**
```yaml
delta:
  lake:
    path: C:/my-delta-tables  # Windows path
    # OR
    path: /home/user/delta-tables  # Linux path
```

**Configure Delta Sharing Server:**
```yaml
delta:
  sharing:
    server-url: https://your-server.com/delta-sharing
    token: your-auth-token
```

After changing config, restart the application.

---

## Common Tasks

### Build Only (No Tests)
```bash
mvn clean package -DskipTests
```

### Build and Run Tests
```bash
mvn clean package
```

### Run Tests Only
```bash
mvn test
```

### View Application Logs
```bash
# Logs are printed to console when running
# Or check Spring Boot's default log file:
# logs/spring.log (if configured)
```

### Clean Build Artifacts
```bash
mvn clean
```

### Stop the Running App
Press **Ctrl+C** in the terminal

---

## Troubleshooting

### "Maven command not found"
- Install Maven (see `MAVEN_SETUP.md`)
- Restart your terminal after installing

### Port 8080 already in use
- Change port in `application.yml` or:
  ```bash
  java -jar -Dserver.port=9090 target/spark-delta-sharing-app-1.0.0.jar
  ```

### Build fails with dependency errors
- Clear Maven cache: `mvn clean`
- Check internet connection
- Try: `mvn clean -U package` (update dependencies)

### Spark/Java version issues
- Check Java version: `java -version` (should be 11+)
- Verify Spark compatibility with your Java version

### Application won't start
- Check if port 8080 is available
- Review logs for error messages
- Ensure Java is properly installed

---

## Next Steps

1. **Read Full Documentation**: Open `README.md`
2. **Test the APIs**: Use cURL examples above
3. **Create Sample Data**: Follow "Testing with Sample Data" section
4. **Extend the App**: Add custom endpoints in `DeltaController.java`
5. **Integrate Delta Sharing**: Update `DeltaSharingService.java` with real server details

---

## Useful Resources

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Apache Spark**: https://spark.apache.org/
- **Delta Lake**: https://delta.io/
- **Delta Sharing**: https://github.com/delta-io/delta-sharing
- **Maven Docs**: https://maven.apache.org/

---

## Key Files to Modify

| File | Purpose | When to Edit |
|------|---------|--------------|
| `application.yml` | Configuration | Change ports, paths, server URLs |
| `DeltaController.java` | REST Endpoints | Add new API endpoints |
| `DeltaService.java` | Business Logic | Add custom query logic |
| `pom.xml` | Dependencies | Add more libraries |

---

**Last Updated**: 2026-06-09
