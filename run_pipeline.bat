@echo off
echo ========================================
echo BRIDGEFLOW: STARTING AUTOMATED PIPELINE
echo ========================================

:: 1. Kompilerer Java-kode
echo [1/3] Compiling Java source...
javac src/*.java
if %errorlevel% neq 0 (
    echo [ERROR] Java compilation failed!
    pause
    exit /b %errorlevel%
)

:: 2. Kjører Java-motor for å transformere data
echo [2/3] Running Java Model Extractor...
java -cp src ModelExtractor
if %errorlevel% neq 0 (
    echo [ERROR] Java execution failed!
    pause
    exit /b %errorlevel%
)

:: 3. Kjører Node.js for å generere rapport
echo [3/3] Generating Engineering Report...
node scripts/report_generator.js

echo ========================================
echo PIPELINE COMPLETE! Check /output folder.
echo ========================================
pause