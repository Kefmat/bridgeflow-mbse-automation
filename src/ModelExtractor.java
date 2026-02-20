import java.io.*;
import java.util.*;

/**
 * Håndterer transformasjon fra legacy CSV til en strukturert systemmodell i JSON.
 * Implementerer logikk for å koble krav til riktige systemkomponenter.
 * Fungerer som broen i den "digitale tråden".
 */
public class ModelExtractor {
    public static void main(String[] args) {
        String csvFile = "legacy_excel/VBA_Export.csv";
        String jsonOutputFile = "requirements.json";
        List<Requirement> requirementList = new ArrayList<>();

        // Definerer standardkomponenter i systemet
        Component engine = new Component("Propulsion System", "Hardware");
        Component flightControl = new Component("Flight Control Unit", "Software");

        System.out.println(">>> BridgeFlow: Starter avansert datatransformasjon...");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Hopper over overskriften i CSV-filen

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values.length >= 4) {
                    // Logikk: Velg komponent basert på data i kolonne 3 (Status/Kategori)
                    // Her simulerer vi at "Software"-status mapper kravet til Flight Control
                    Component owner = values[2].equalsIgnoreCase("Software") ? flightControl : engine;

                    // Oppretter objekt og sjekker integritet før vi legger det til i modellen
                    Requirement req = new Requirement(values[0], values[1], values[3], owner);
                    
                    if (req.isValid()) {
                        requirementList.add(req);
                    } else {
                        System.out.println("[WARNING] Ugyldig data funnet for ID: " + values[0]);
                    }
                }
            }
            
            // Verifiserer sikkerhetsregler (Safety Constraints) før eksport
            verifySafetyConstraints(requirementList, Arrays.asList(engine, flightControl));
            
            writeToJson(requirementList, jsonOutputFile);
        } catch (IOException e) {
            System.err.println("[CRITICAL ERROR] Pipeline avbrutt: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Sjekker om maskinvarekomponenter mangler kritiske sikkerhetskrav.
     */
    private static void verifySafetyConstraints(List<Requirement> reqs, List<Component> comps) {
        for (Component c : comps) {
            if (c.getCategory().equals("Hardware")) {
                boolean hasHighPrio = reqs.stream()
                    .anyMatch(r -> r.getOwnerComponent() == c && r.getPriority().equals("High"));
                
                if (!hasHighPrio) {
                    System.out.println("[SAFETY WARNING] Komponent '" + c.getName() + "' mangler High-Priority krav!");
                }
            }
        }
    }

    /**
     * Serialiserer listen med krav til en JSON-fil.
     * Eksporterer den fullstendige systemmodellen til JSON-format.
     */
    private static void writeToJson(List<Requirement> list, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("[\n");
            for (int i = 0; i < list.size(); i++) {
                Requirement r = list.get(i);
                
                // En enkel regel: Hardware uten High priority flagges som en advarsel
                boolean isViolation = r.getOwnerComponent().getCategory().equals("Hardware") && 
                                     !r.getPriority().equals("High");

                writer.write("  {\n");
                writer.write("    \"id\": \"" + r.getId() + "\",\n");
                writer.write("    \"name\": \"" + r.getName() + "\",\n");
                writer.write("    \"priority\": \"" + r.getPriority() + "\",\n");
                writer.write("    \"component\": \"" + r.getOwnerComponent().getName() + "\",\n");
                writer.write("    \"category\": \"" + r.getOwnerComponent().getCategory() + "\",\n");
                writer.write("    \"safety_check\": \"" + (isViolation ? "WARNING" : "OK") + "\",\n");
                writer.write("    \"status\": \"Validated via Java\"\n");
                writer.write("  }");
                if (i < list.size() - 1) writer.write(",");
                writer.write("\n");
            }
            writer.write("]");
            System.out.println("[SUCCESS] Systemmodell med relasjoner lagret til JSON.");
        }
    }
}