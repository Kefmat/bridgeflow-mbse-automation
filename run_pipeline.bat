@echo off
title BridgeFlow MBSE Pipeline
cls
echo ========================================
echo   BRIDGEFLOW: END-TO-END AUTOMATION
echo ========================================

:: 1. Kompilering
echo [1/4] Kompilerer Java-komponenter...
javac src/*.java
if %errorlevel% neq 0 (
    echo [FEIL] Kompilering feilet. Sjekk Java-koden.
    pause
    exit /b 1
)

:: 2. Kvalitetssjekk (Unit Tests)
echo [2/4] Kjorer Quality Gate (Enhetstester)...
java -cp src RequirementTest
if %errorlevel% neq 0 (
    echo [STOPP] Quality Gate feilet! Pipelinen er avbrutt for a hindre korrupte data.
    pause
    exit /b 1
)

:: 3. Transformasjon
echo [3/4] Transformerer modell-data (CSV til JSON)...
java -cp src ModelExtractor
if %errorlevel% neq 0 exit /b 1

:: 4. Rapport
echo [4/4] Genererer visuelt dashboard...
node scripts/report_generator.js

echo ========================================
echo   PIPELINE FULLFORT - Sjekk /output/
echo ========================================
:: Apner det visuelle dashboardet automatisk i standard nettleser
start "" "output\dashboard.html"
pause