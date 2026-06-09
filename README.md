# Spark Delta Sharing Spring Boot App

A simple REST API for learning Apache Spark, Delta Lake, and Delta Sharing integration with Spring Boot.

## Project Structure

```
spark-delta-sharing-app/
├── pom.xml                                  # Maven configuration with dependencies
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── Application.java            # Main Spring Boot application
│   │   │   ├── config/
│   │   │   │   └── SparkConfig.java        # SparkSession bean configuration
│   │   │   ├── controller/
│   │   │   │   └── DeltaController.java    # REST endpoints
│   │   │   ├── service/
│   │   │   │   ├── DeltaService.java       # Spark SQL operations
│   │   │   │   └── DeltaSharingService.java # Delta Sharing integration
│   │   │   └── error/
│   │   │       └── GlobalExceptionHandler.java # Exception handling
│   │   └── resources/
│   │       └── application.yml              # Application configuration
│   └── test/
└── README.md
```

## Prerequisites

- **Java 17+** installed
- **Maven 3.6+** installed
- **Spark 3.5.0** compatible environment

## Quick Start

### 1. Build the Project

```bash
cd c:\Users\breno\Desktop\Java\1
mvn clean package
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 3. Test Endpoints

#### Health Check
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

#### List Delta Tables
```bash
curl http://localhost:8080/api/delta/tables
```

#### Read Delta Table
```bash
curl http://localhost:8080/api/delta/table/my_table
```

#### Delta Sharing Profile
```bash
curl http://localhost:8080/api/sharing/profile
```

#### List Shared Tables
```bash
curl http://localhost:8080/api/sharing/tables
```

## Configuration

Edit `src/main/resources/application.yml` to customize:

```yaml
delta:
  lake:
    path: file:///tmp/delta              # Local Delta Lake path
  sharing:
    server-url: http://localhost:8080    # Delta Sharing server
    token: demo-token                    # Authentication token
```

## API Endpoints

### Health & Status
- `GET /api/health` — Health check

### Delta Lake
- `GET /api/delta/tables` — List available Delta tables
- `GET /api/delta/table/{tableName}` — Read specific Delta table (first 10 rows)

### Delta Sharing
- `GET /api/sharing/profile` — Get sharing profile info
- `GET /api/sharing/tables` — List shared tables

## Setting Up Sample Delta Tables

To test with sample data, create a Delta table locally:

### Using Spark Shell

```bash
spark-shell --packages io.delta:delta-core_2.12:3.1.0

# In the shell:
val data = Seq((1, "Alice"), (2, "Bob"), (3, "Charlie")).toDF("id", "name")
data.write.format("delta").mode("overwrite").save("file:///tmp/delta/users")
```

Then query it:
```bash
curl http://localhost:8080/api/delta/table/users
```

## Key Features

- ✅ **Spring Boot Integration** — Simple dependency injection and bean management
- ✅ **Spark Session Bean** — Shared SparkSession for efficient resource use
- ✅ **Delta Lake Support** — Read Delta format tables with schema introspection
- ✅ **Delta Sharing Ready** — Configuration for Delta Sharing server integration
- ✅ **REST API** — Easy-to-use JSON endpoints
- ✅ **Error Handling** — Global exception handler with meaningful responses
- ✅ **Logging** — SLF4J logging for debugging

## Extending the App

### Add a Custom Endpoint

1. Add a method in `DeltaService.java`:
   ```java
   public Map<String, Object> customQuery(String query) {
       // Your logic here
   }
   ```

2. Add corresponding endpoint in `DeltaController.java`:
   ```java
   @PostMapping("/custom-query")
   public ResponseEntity<Map<String, Object>> customQuery(@RequestBody Map<String, String> request) {
       return ResponseEntity.ok(deltaService.customQuery(request.get("query")));
   }
   ```

### Connect to Real Delta Sharing Server

1. Update `application.yml`:
   ```yaml
   delta:
     sharing:
       server-url: https://your-delta-sharing-server.com
       token: your-actual-token
   ```

2. Implement client logic in `DeltaSharingService.java` using the Delta Sharing Java client library.

## Troubleshooting

### Spark Dependency Issues
If you encounter Spark-related errors, ensure Java version matches:
```bash
java -version  # Should be 11+
```

### Port 8080 Already in Use
Change the port in `application.yml`:
```yaml
server:
  port: 9090
```

### Delta Table Not Found
Ensure the Delta table exists at the configured path. Check logs:
```bash
# Logs show the attempted path
```

## Dependencies

- **Spring Boot** 3.2.0 — Web framework
- **Apache Spark** 3.5.0 — Data processing
- **Delta Lake** 3.1.0 — Delta format support
- **Delta Sharing Client** 0.8.1 — Delta Sharing protocol
- **Lombok** — Boilerplate reduction
- **SLF4J** — Logging

## License

MIT

## Further Learning

- [Apache Spark Docs](https://spark.apache.org/docs/latest/)
- [Delta Lake Guide](https://docs.delta.io/)
- [Delta Sharing Protocol](https://github.com/delta-io/delta-sharing)
- [Spring Boot Reference](https://spring.io/projects/spring-boot)
