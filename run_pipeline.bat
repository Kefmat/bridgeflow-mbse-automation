@echo off
echo ========================================
echo BRIDGEFLOW: QUALITY-GATED PIPELINE
echo ========================================

:: 1. Kompiler alt
echo [1/4] Compiling source and tests...
javac src/*.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)

:: 2. KJØR TESTER
echo [2/4] Running Automated Quality Tests...
java -cp src RequirementTest
if %errorlevel% neq 0 (
    echo [CRITICAL ERROR] Quality tests failed! Pipeline aborted.
    echo Report will not be generated until code is fixed.
    pause
    exit /b 1
)

:: 3. Transformer data
echo [3/4] Running Java Model Extractor...
java -cp src ModelExtractor

:: 4. Generer rapport
echo [4/4] Generating Engineering Report...
node scripts/report_generator.js

echo ========================================
echo SUCCESS: Data validated and report generated!
echo ========================================
pause