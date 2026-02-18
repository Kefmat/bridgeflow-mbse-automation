# BridgeFlow-MBSE: Digital Thread Automation

**BridgeFlow-MBSE** er en Proof-of-Concept (PoC) utviklet for å demonstrere automatisering av dataflyt i komplekse systems engineering-prosjekter. Prosjektet broer gapet mellom ustrukturerte legacy-data (Excel/VBA) og strukturerte modell-data ved bruk av Java og moderne DevOps-prinsipper.


## Systemarkitektur

Prosjektet følger en modulær arkitektur som sikrer dataintegritet gjennom hele livssyklusen fra eksport til ferdig rapport.

```mermaid
graph TD
    subgraph "Legacy Domain (Excel/VBA)"
        A[Ingeniør-data i Excel] -->|VBA Export Script| B(VBA_Export.csv)
    end

    subgraph "DevOps Pipeline (Java Engine)"
        B -->|File I/O| C[ModelExtractor.java]
        C -->|Object Validation| D{Requirement.java}
        D -->|Valid Data| E[JSON Transformer]
    end

    subgraph "Reporting Domain (JS/Node)"
        E -->|Intermediate Data| F(requirements.json)
        F -->|Processing| G[report_generator.js]
        G -->|Output| H[Engineering_Status.md]
    end

    style A fill:#f9f,stroke:#333,stroke-width:2px
    style H fill:#bbf,stroke:#333,stroke-width:4px

Teknisk Stack & Funksjonalitet
1. Legacy Integration (VBA)
Kilde: Simulerer ingeniørdata lagret i komplekse Excel-ark.

Logikk: Bruker VBA-skript for å eksportere krav til et flatt filformat (CSV) for videre prosessering.

2. Model Engine (Java)
Requirement.java: En robust klasse som transformerer rådata til objekter med innebygd validering logikk.

ModelExtractor.java: Fungerer som en "vaktpost" (Gatekeeper) som leser legacy-data, forkaster ugyldige krav, og eksporterer til et standardisert JSON-format.

Fokus: Objektorientert programmering (OOP) og datavalidering.

3. Reporting & DevOps (JavaScript/Node.js)
report_generator.js: Konsumerer de validerte dataene og produserer automatiserte rapporter i Markdown.

Automatisering: Sikrer "Single Source of Truth" ved å fjerne manuelt "kopi-lim" arbeid.


## Slik kjøres pipelinen
For å sikre en sømløs arbeidsflyt brukes en automatiseringsfil som orkestrerer alle stegene.

Forutsetninger
Java JDK installert

Node.js installert

Kjøring (Windows)
Dobbeltklikk på run_pipeline.bat eller kjør i terminal:

Bash
.\run_pipeline.bat
Kjøring (Manuell)
Kompiler: javac src/*.java

Transformer: java -cp src ModelExtractor

Rapporter: node scripts/report_generator.js


## Designfilosofi

Modularitet: Hvert steg i pipelinen er uavhengig. Vi kan bytte ut kilden (f.eks. fra CSV til REST API) uten å endre rapporteringslogikken.

Skalerbarhet: Java-motoren er forberedt for integrasjon mot tunge MBSE-verktøy som Cameo Systems Modeler eller 3DExperience.

Sporbarhet: Ved å bruke en "Digital Thread"-tilnærming sikrer vi at hvert krav kan spores fra kilde til rapport uten menneskelige feil.

Utviklet som et teknisk dypdykk i DevOps for Systems Engineering.
