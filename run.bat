@echo off
REM ============================================
REM Spark Delta Sharing App - Run Script
REM ============================================

echo.
echo [INFO] Starting Spark Delta Sharing Application...
echo [INFO] Application will be available at: http://localhost:8080
echo [INFO] Press Ctrl+C to stop the application
echo.

cd /d "%~dp0"

if exist "build\libs\spark-delta-sharing-app-1.0.0.jar" (
    echo [INFO] Running built JAR...
    java -jar build\libs\spark-delta-sharing-app-1.0.0.jar
) else (
    echo [INFO] JAR not found. Running via Gradle...
    call gradlew.bat bootRun
)
