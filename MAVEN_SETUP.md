# Maven Setup Instructions for Windows

Maven is not currently installed on your system. Here's how to set it up:

## Option 1: Download and Install Maven Manually

### Steps:

1. **Download Maven**
   - Go to https://maven.apache.org/download.cgi
   - Download the binary zip archive (e.g., `apache-maven-3.9.6-bin.zip`)

2. **Extract Maven**
   - Extract the ZIP file to a location like: `C:\Program Files\maven` or `C:\apache-maven`
   - The folder should contain: `bin`, `lib`, `boot`, etc.

3. **Add Maven to PATH**
   - Open System Environment Variables:
     - Press `Win + X` → Select "System"
     - Click "Advanced system settings"
     - Click "Environment Variables"
   
   - Add a new SYSTEM variable:
     - Variable name: `MAVEN_HOME`
     - Variable value: `C:\Program Files\maven` (path where you extracted Maven)
   
   - Edit the PATH variable:
     - Find the PATH variable in System variables
     - Click "Edit"
     - Click "New"
     - Add: `%MAVEN_HOME%\bin`
     - Click OK and apply changes

4. **Verify Installation**
   - Open a new command prompt or PowerShell
   - Run: `mvn --version`
   - You should see Maven version information

## Option 2: Install Using Chocolatey (if available)

If you have Chocolatey installed:

```powershell
choco install maven
```

## Option 3: Use Windows Package Manager (Windows 11+)

```powershell
winget install Apache.Maven
```

## After Maven is Installed

Once Maven is set up, build the project:

```bash
cd c:\Users\breno\Desktop\Java\1
mvn clean package -DskipTests
```

Or use the provided batch script:

```bash
.\build.bat
```

## Troubleshooting

**Problem**: "mvn is not recognized as an internal or external command"
- **Solution**: Make sure you added Maven's `bin` directory to your PATH and restarted your terminal

**Problem**: "JAVA_HOME is not set"
- **Solution**: Set JAVA_HOME system variable to your Java installation (e.g., `C:\Program Files\Java\jdk-21`)

**Problem**: Slow downloads during first build
- **Solution**: This is normal! Maven is downloading all dependencies. It may take 5-10 minutes on first build.

## Testing the Build

After Maven builds successfully, you should see:
```
[INFO] BUILD SUCCESS
[INFO] Total time: ...
```

The JAR file will be at: `target/spark-delta-sharing-app-1.0.0.jar`
