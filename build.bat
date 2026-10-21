@echo off
setlocal
title Hospital Management System - Build
chcp 65001 >nul

cd /d "%~dp0"

echo ===================================================
echo     Hospital Management System - Maven Build
echo ===================================================
echo.

where mvn >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Maven was not found in PATH!
    echo Please install Maven 3.9+ and add it to PATH.
    pause
    exit /b 1
)

echo [INFO] Running 'mvn clean package -DskipTests'...
call mvn clean package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Build failed!
    pause
    exit /b 1
)

echo.
echo ===================================================
echo  Build successful!
echo  Executable JAR: target\hospital-management-system-1.0.0.jar
echo ===================================================
echo.
pause
