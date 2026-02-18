# BridgeFlow: MBSE Automation Tool

Dette prosjektet er en Proof-of-Concept for automatisering av dataflyt i komplekse systemutviklingsprosjekter.

### Nøkkelfunksjoner
- **Model Extractor (Java):** Simulerer uthenting av metadata fra systemmodeller.
- **Reporting Engine (Node.js):** Automatiserer transformasjon fra rå JSON-data til strukturerte ingeniørrapporter.
- **Interoperabilitet:** Viser hvordan man kan koble sammen ulike fagverktøy for å sikre "Single Source of Truth".

### Hvordan kjøre prosjektet
1. Kompiler og kjør Java-modul:
   `javac src/ModelExtractor.java && java -cp src ModelExtractor`
2. Generer rapport med Node.js:
   `node scripts/report_generator.js`