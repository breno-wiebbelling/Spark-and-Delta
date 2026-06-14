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
echo [INFO] Building Spring Boot Application...
cd /d "%~dp0"
call gradlew.bat clean bootJar

if %ERRORLEVEL% EQU 0 (
    echo.
    echo [SUCCESS] Build completed successfully!
    echo [INFO] JAR file: build\libs\spark-delta-sharing-app-1.0.0.jar
    echo.
    echo To run the application, use:
    echo   gradlew.bat bootRun
    echo OR
    echo   java -jar build\libs\spark-delta-sharing-app-1.0.0.jar
) else (
    echo [ERROR] Build failed. Check the output above for details.
    exit /b 1
)
