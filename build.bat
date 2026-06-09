@echo off
REM ============================================
REM Spark Delta Sharing App - Setup & Build Script
REM ============================================

echo.
echo [INFO] Checking Java Installation...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java is not installed or not in PATH
    exit /b 1
)

echo.
echo [INFO] Checking Maven Installation...
mvn --version
if %ERRORLEVEL% NEQ 0 (
    echo [WARNING] Maven is not installed or not in PATH
    echo [INFO] Please install Maven from: https://maven.apache.org/download.cgi
    echo [INFO] Add Maven bin directory to your PATH environment variable
    exit /b 1
)

echo.
echo [INFO] Building Spring Boot Application...
cd /d "%~dp0"
mvn clean package -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [SUCCESS] Build completed successfully!
    echo [INFO] JAR file: target\spark-delta-sharing-app-1.0.0.jar
    echo.
    echo To run the application, use:
    echo   mvn spring-boot:run
    echo OR
    echo   java -jar target\spark-delta-sharing-app-1.0.0.jar
) else (
    echo [ERROR] Build failed. Check the output above for details.
    exit /b 1
)
