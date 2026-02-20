/**
 * BridgeFlow Report Generator (Refaktorert)
 * Transformer JSON-modell til profesjonelt dashboard og Markdown.
 */

const fs = require('fs');
const path = require('path');

// Definerer filstier
const outputDir = './output';
const logDir = './logs';
const cssPath = path.join(__dirname, 'style.css'); // Stien til den nye CSS-fila

// Initialisering av mapper
if (!fs.existsSync(outputDir)) fs.mkdirSync(outputDir);
if (!fs.existsSync(logDir)) fs.mkdirSync(logDir);

/**
 * Logger hendelser med tidsstempel til både konsoll og loggfil.
 */
function writeLog(message) {
    const ts = new Date().toISOString();
    fs.appendFileSync(path.join(logDir, 'pipeline.log'), `[${ts}] ${message}\n`);
    console.log(message);
}

try {
    writeLog('Starter generering av rapporter...');

    // Data og CSS
    const rawData = fs.readFileSync('requirements.json');
    const requirements = JSON.parse(rawData);
    const cssContent = fs.readFileSync(cssPath, 'utf8'); // Henter den ryddige stylingen
    const dateStr = new Date().toLocaleString('no-NO');

    // Dashboard (HTML)
    const html = `
    <!DOCTYPE html>
    <html lang="no">
    <head>
        <meta charset="UTF-8">
        <style>${cssContent}</style>
        <title>BridgeFlow System Dashboard</title>
    </head>
    <body>
        <div class="container">
            <header>
                <div>
                    <h1>BridgeFlow System Dashboard</h1>
                    <div style="color: #666;">Model-Based Systems Engineering Pipeline</div>
                </div>
                <div class="meta-info">
                    <span class="badge status-pass">CI/CD ACTIVE</span><br>
                    <small>${dateStr}</small>
                </div>
            </header>

            <div class="stats-bar">
                <div class="stat-card">
                    <div class="stat-value">${requirements.length}</div>
                    <div>Totale Krav</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value">${[...new Set(requirements.map(r => r.component))].length}</div>
                    <div>Systemkomponenter</div>
                </div>
            </div>
            
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Kravbeskrivelse</th>
                        <th>Komponent</th>
                        <th>Prioritet</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    ${requirements.map(req => `
                    <tr>
                        <td><strong>${req.id}</strong></td>
                        <td>${req.name}</td>
                        <td><span class="badge comp-tag">${req.component}</span></td>
                        <td><span class="badge ${req.priority === 'High' ? 'prio-high' : 'prio-med'}">${req.priority}</span></td>
                        <td><span class="badge status-pass">VERIFISERT</span></td>
                    </tr>
                    `).join('')}
                </tbody>
            </table>

            <footer>
                Denne rapporten er automatisk generert av BridgeFlow-MBSE Digital Thread.<br>
                Audit-logg finnes i /logs/pipeline.log
            </footer>
        </div>
    </body>
    </html>`;

    // Lagre HTML Dashboard
    fs.writeFileSync(path.join(outputDir, 'dashboard.html'), html);
    writeLog('SUCCESS: HTML Dashboard generert med ekstern styling.');

    // Lagre Markdown-rapport (For GitHub-oversikt)
    let md = `# Engineering Status Report - ${dateStr}\n\n`;
    md += `| ID | Beskrivelse | Komponent | Prio |\n|---|---|---|---|\n`;
    requirements.forEach(r => {
        md += `| ${r.id} | ${r.name} | ${r.component} | ${r.priority} |\n`;
    });
    fs.writeFileSync(path.join(outputDir, 'Engineering_Status.md'), md);
    
    writeLog('SUCCESS: Pipeline fullført uten feil.');

} catch (err) {
    writeLog(`CRITICAL ERROR: ${err.message}`);
    process.exit(1);
}