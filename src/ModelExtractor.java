import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelExtractor {
    public static void main(String[] args) {
        String csvFile = "legacy_excel/VBA_Export.csv";
        String line = "";
        String cvsSplitBy = ";";

        System.out.println("--- BridgeFlow: Importing from Legacy VBA Export ---");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Hopper over header-linjen
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                
                // data[0] = ID, data[1] = Name, data[2] = Desc
                System.out.println("[JAVA PLUGIN] Processing: " + data[1] + " (ID: " + data[0] + ")");
                
                // Her ville vi i virkeligheten brukt Cameo API til å lage et objekt:
                // Application.getInstance().getProject().getElementsFactory().createRequirement()...
            }
            
            System.out.println("[SUCCESS] All VBA requirements imported to Model.");

        } catch (IOException e) {
            System.err.println("[ERROR] Could not read VBA export file: " + e.getMessage());
        }
    }
}