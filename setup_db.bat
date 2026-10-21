@echo off
setlocal enabledelayedexpansion
title Hospital Management System - Database Setup
chcp 65001 >nul

echo ===================================================
echo     Hospital Management System - Database Setup
echo ===================================================
echo.

set "DB_PASS=2805"
set "DB_USER=root"
set "MYSQL_CMD=mysql"

where mysql >nul 2>&1
if %ERRORLEVEL% equ 0 goto found_mysql

echo [INFO] mysql not in PATH. Searching known directories...
for /d %%D in ("C:\Program Files\MySQL\MySQL Server*") do (
    if exist "%%D\bin\mysql.exe" (
        set "MYSQL_CMD=%%D\bin\mysql.exe"
        goto found_mysql
    )
)
for /d %%D in ("C:\Program Files (x86)\MySQL\MySQL Server*") do (
    if exist "%%D\bin\mysql.exe" (
        set "MYSQL_CMD=%%D\bin\mysql.exe"
        goto found_mysql
    )
)
for /d %%D in ("C:\xampp\mysql\bin") do (
    if exist "%%D\mysql.exe" (
        set "MYSQL_CMD=%%D\mysql.exe"
        goto found_mysql
    )
)

echo [ERROR] MySQL client mysql.exe was not found.
echo Please verify MySQL is installed and available.
pause
exit /b 1

:found_mysql
echo [INFO] Using MySQL client: "!MYSQL_CMD!"
echo [INFO] Testing MySQL connection for user %DB_USER% with provided password...

"!MYSQL_CMD!" -u %DB_USER% -p%DB_PASS% -e "SELECT 1;" >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Connection failed for user '%DB_USER%' with password '%DB_PASS%'.
    echo Please ensure MySQL is running and the password is correct.
    pause
    exit /b 1
)

echo [INFO] Connection successful. Initializing database schema...
"!MYSQL_CMD!" -u %DB_USER% -p%DB_PASS% < "%~dp0db\schema.sql"
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Failed to execute db\schema.sql.
    pause
    exit /b 1
)

echo.
echo ===================================================
echo  Database 'hospital_management' setup successfully!
echo ===================================================
echo Default Accounts:
echo   - Admin:         admin / admin123
echo   - Receptionist:  reception / reception123
echo.
pause
