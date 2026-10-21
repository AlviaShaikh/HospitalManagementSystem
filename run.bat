@echo off
setlocal enabledelayedexpansion
title Hospital Management System
chcp 65001 >nul

cd /d "%~dp0"

echo ===================================================
echo          Hospital Management System
echo ===================================================
echo.

set "DB_URL=jdbc:mysql://localhost:3306/hospital_management?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
set "DB_USERNAME=root"
set "DB_PASSWORD=2805"

:: Locate Java
set "JAVA_CMD=java"
where java >nul 2>&1
if %ERRORLEVEL% equ 0 goto found_java

echo [INFO] Java not in PATH. Searching known directories...
for /d %%D in ("C:\Program Files\Java\jdk*") do (
    if exist "%%D\bin\java.exe" (
        set "JAVA_CMD=%%D\bin\java.exe"
        goto found_java
    )
)

echo [ERROR] Java was not found on your system!
echo Please install JDK 17+ and add it to PATH.
pause
exit /b 1

:found_java

:: Check if MySQL command is available
set "MYSQL_CMD="
where mysql >nul 2>&1
if %ERRORLEVEL% equ 0 (
    set "MYSQL_CMD=mysql"
    goto check_db
)

for /d %%D in ("C:\Program Files\MySQL\MySQL Server*") do (
    if exist "%%D\bin\mysql.exe" (
        set "MYSQL_CMD=%%D\bin\mysql.exe"
        goto check_db
    )
)
for /d %%D in ("C:\Program Files (x86)\MySQL\MySQL Server*") do (
    if exist "%%D\bin\mysql.exe" (
        set "MYSQL_CMD=%%D\bin\mysql.exe"
        goto check_db
    )
)
for /d %%D in ("C:\xampp\mysql\bin") do (
    if exist "%%D\mysql.exe" (
        set "MYSQL_CMD=%%D\mysql.exe"
        goto check_db
    )
)

:check_db
if defined MYSQL_CMD (
    echo [INFO] Verifying database...
    "!MYSQL_CMD!" -u %DB_USERNAME% -p%DB_PASSWORD% -e "USE hospital_management;" >nul 2>&1
    if !ERRORLEVEL! neq 0 (
        echo [INFO] Database 'hospital_management' not found. Setting up schema...
        "!MYSQL_CMD!" -u %DB_USERNAME% -p%DB_PASSWORD% < "%~dp0db\schema.sql" >nul 2>&1
        if !ERRORLEVEL! equ 0 (
            echo [INFO] Database initialized successfully!
        ) else (
            echo [WARNING] Could not auto-initialize database. Starting application anyway...
        )
    ) else (
        echo [INFO] Database verified.
    )
)

:: Check if packaged jar exists
if exist "%~dp0target\hospital-management-system-1.0.0.jar" goto launch_jar

:: Fallback: build with Maven if jar is missing
where mvn >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Executable jar not found and Maven is not in PATH.
    echo Please install Maven or run build.bat.
    pause
    exit /b 1
)

echo [INFO] Building runnable JAR with Maven...
call mvn clean package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Build failed.
    pause
    exit /b 1
)

:launch_jar
echo [INFO] Launching Hospital Management System...
"!JAVA_CMD!" -jar "%~dp0target\hospital-management-system-1.0.0.jar"
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Application exited with code %ERRORLEVEL%.
    pause
    exit /b %ERRORLEVEL%
)
exit /b 0
