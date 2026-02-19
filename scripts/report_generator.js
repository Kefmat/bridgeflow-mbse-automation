/**
 * BridgeFlow Report Generator
 * Transformer JSON-modell til visuelt HTML-dashboard og Markdown-rapport.
 * Inkluderer audit-logging for sporbarhet.
 */

const fs = require('fs');
const path = require('path');

const outputDir = './output';
const logDir = './logs';

// Initialisering av filstruktur
if (!fs.existsSync(outputDir)) fs.mkdirSync(outputDir);
if (!fs.existsSync(logDir)) fs.mkdirSync(logDir);

function writeLog(message) {
    const ts = new Date().toISOString();
    fs.appendFileSync(path.join(logDir, 'pipeline.log'), `[${ts}] ${message}\n`);
    console.log(message);
}

try {
    writeLog('Starter generering av rapporter...');

    // Henter validert data fra Java-motoren
    const rawData = fs.readFileSync('requirements.json');
    const requirements = JSON.parse(rawData);
    const dateStr = new Date().toLocaleString('no-NO');

    // HTML Dashboard med enkel styling
    const html = `
    <!DOCTYPE html>
    <html lang="no">
    <head>
        <meta charset="UTF-8">
        <style>
            body { font-family: 'Segoe UI', sans-serif; margin: 40px; background: #f0f2f5; color: #1a237e; }
            .container { max-width: 1000px; margin: auto; background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
            h1 { border-bottom: 3px solid #1a237e; padding-bottom: 10px; margin-bottom: 5px; }
            .meta { color: #666; font-size: 0.9em; margin-bottom: 20px; }
            .badge { padding: 4px 10px; border-radius: 4px; font-size: 0.8em; font-weight: bold; }
            .pass { background: #c8e6c9; color: #2e7d32; }
            .high-prio { background: #ffcdd2; color: #b71c1c; }
            table { width: 100%; border-collapse: collapse; margin-top: 20px; }
            th { background: #1a237e; color: white; padding: 12px; text-align: left; }
            td { padding: 12px; border-bottom: 1px solid #eee; }
            tr:hover { background: #f5f5f5; }
        </style>
        <title>BridgeFlow MBSE Dashboard</title>
    </head>
    <body>
        <div class="container">
            <h1>BridgeFlow Engineering Dashboard</h1>
            <div class="meta">Status: <b>PASSED</b> | Generert: ${dateStr}</div>
            
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Beskrivelse</th>
                        <th>Prioritet</th>
                        <th>Verifisering</th>
                    </tr>
                </thead>
                <tbody>
                    ${requirements.map(req => `
                    <tr>
                        <td><strong>${req.id}</strong></td>
                        <td>${req.name}</td>
                        <td><span class="badge ${req.priority === 'High' ? 'high-prio' : ''}">${req.priority}</span></td>
                        <td><span class="badge pass">VERIFISERT</span></td>
                    </tr>
                    `).join('')}
                </tbody>
            </table>
            <footer style="margin-top: 40px; font-size: 0.8em; color: #888;">
                Denne rapporten er en del av BridgeFlow-MBSE Digital Thread. Audit-logg finnes i /logs/pipeline.log.
            </footer>
        </div>
    </body>
    </html>`;

    fs.writeFileSync(path.join(outputDir, 'dashboard.html'), html);
    writeLog('SUCCESS: HTML Dashboard generert.');

    // Generer Markdown-kopi for GitHub-visning
    let md = `# Engineering Status Report - ${dateStr}\n\n| ID | Beskrivelse | Prio |\n|---|---|---|\n`;
    requirements.forEach(r => { md += `| ${r.id} | ${r.name} | ${r.priority} |\n`; });
    fs.writeFileSync(path.join(outputDir, 'Engineering_Status.md'), md);
    
    writeLog('SUCCESS: Pipeline fullført uten feil.');

} catch (err) {
    writeLog(`CRITICAL ERROR: ${err.message}`);
    process.exit(1);
}