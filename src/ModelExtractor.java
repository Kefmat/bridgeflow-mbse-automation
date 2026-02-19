import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Håndterer dataflyt fra Legacy CSV til moderne JSON-format.
 * Fungerer som broen i den "digitale tråden".
 */
public class ModelExtractor {
    public static void main(String[] args) {
        String csvFile = "legacy_excel/VBA_Export.csv";
        String jsonOutputFile = "requirements.json";
        List<Requirement> requirementList = new ArrayList<>();

        System.out.println(">>> BridgeFlow: Starter datatransformasjon...");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Hopper over overskriften i CSV-filen

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length >= 4) {
                    // Oppretter objekt og sjekker integritet før vi legger det til i modellen
                    Requirement req = new Requirement(values[0], values[1], values[3]);
                    if (req.isValid()) {
                        requirementList.add(req);
                    } else {
                        System.out.println("[WARNING] Ignorerer ugyldig krav: " + values[0]);
                    }
                }
            }
            writeToJson(requirementList, jsonOutputFile);
        } catch (IOException e) {
            System.err.println("[CRITICAL ERROR] Kunne ikke lese kildedata: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Serialiserer listen med krav til en JSON-fil.
     */
    private static void writeToJson(List<Requirement> list, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("[\n");
            for (int i = 0; i < list.size(); i++) {
                Requirement r = list.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": \"" + r.getId() + "\",\n");
                writer.write("    \"name\": \"" + r.getName() + "\",\n");
                writer.write("    \"priority\": \"" + r.getPriority() + "\",\n");
                writer.write("    \"status\": \"Validated via Java\"\n");
                writer.write("  }");
                if (i < list.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("]");
            System.out.println("[SUCCESS] Modell eksportert til JSON.");
        }
    }
}