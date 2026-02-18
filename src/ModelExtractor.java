import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModelExtractor {
    public static void main(String[] args) {
        // Filstier
        String csvFile = "legacy_excel/VBA_Export.csv";
        String jsonOutputFile = "requirements.json";
        List<Requirement> requirementList = new ArrayList<>();

        System.out.println(">>> BridgeFlow: Starting Pipeline (VBA -> Java -> JSON)");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Hopper over header-linjen i CSV-en

            while ((line = br.readLine()) != null) {
                // Splitter på semikolon slik vi definerte i VBA-eksporten
                String[] values = line.split(";");
                
                if (values.length >= 4) {
                    Requirement req = new Requirement(values[0], values[1], values[3]);

                    if (req.isValid()) {
                        requirementList.add(req);
                        System.out.println("[PARSED] " + req);
                    }
                }
            }

            // Utfører selve eksporten til JSON
            writeToJson(requirementList, jsonOutputFile);

        } catch (IOException e) {
            System.err.println("[ERROR] Fant ikke filen eller feil ved lesing: " + e.getMessage());
        }
    }

    private static void writeToJson(List<Requirement> list, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("[\n");
            
            for (int i = 0; i < list.size(); i++) {
                Requirement r = list.get(i);
                writer.write("  {\n");
                writer.write("    \"id\": \"" + r.getId() + "\",\n");
                writer.write("    \"name\": \"" + r.getName() + "\",\n");
                writer.write("    \"status\": \"Validated via Java\"\n");
                writer.write("  }");
                
                // Legger til komma mellom alle objekter unntatt det siste
                if (i < list.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            
            writer.write("]");
            System.out.println("[SUCCESS] JSON-fil oppdatert: " + filename);
        }
    }
}