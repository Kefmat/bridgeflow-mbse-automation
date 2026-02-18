const fs = require('fs');
const path = require('path');

try {
    // 1. Leser data
    const dataPath = path.join(__dirname, '../requirements.json');
    const requirements = JSON.parse(fs.readFileSync(dataPath, 'utf8'));

    // 2. Bygger rapporten
    let report = "# Universal Systems Engineering Report\n\n";
    report += "## Project: High-Complexity Architecture\n\n";
    report += "| ID | Component | Status | Priority |\n";
    report += "|---|---|---|---|\n";

    requirements.forEach(req => {
        const icon = req.status === "Verified" ? "✅" : "🚧";
        report += `| ${req.id} | ${req.name} | ${icon} ${req.status} | ${req.priority} |\n`;
    });

    // 3. Lagre til output
    const outputPath = path.join(__dirname, '../output/Engineering_Status.md');
    fs.writeFileSync(outputPath, report);

    console.log(">> Success: BridgeFlow generated report in /output/");
} catch (error) {
    console.error(">> Error in report generation:", error.message);
}