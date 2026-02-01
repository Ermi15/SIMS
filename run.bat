@echo off
title Student Information System
echo ========================================
echo   Student Information Management System
echo ========================================
echo.

REM Set JavaFX path - CHANGE THIS if needed
set JAVAFX_PATH=C:\My\JavaFX\javafx-sdk-25.0.2\lib

REM Run the application (ONE LINE)
java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -jar "%~dp0SIMS.jar"

REM Only ONE pause at the end
echo.
echo Application closed.
pause