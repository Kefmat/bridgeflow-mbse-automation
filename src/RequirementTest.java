public class RequirementTest {
    public static void main(String[] args) {
        int failedTests = 0;
        Component testComp = new Component("TestUnit", "Debug");

        System.out.println(">>> Quality Gate: Verifiserer systemmodellen...");

        // Test 1: Gyldig objekt med komponent
        Requirement req1 = new Requirement("REQ-001", "System Check", "High", testComp);
        if (!req1.isValid()) {
            System.err.println("[FAIL] Gyldig krav ble avvist.");
            failedTests++;
        }

        // Test 2: Manglende komponent (Skal feile)
        Requirement req2 = new Requirement("REQ-002", "Invalid Unit", "Low", null);
        if (req2.isValid()) {
            System.err.println("[FAIL] Krav uten komponent ble akseptert.");
            failedTests++;
        }

        if (failedTests == 0) {
            System.out.println("[SUCCESS] Alle enhetstester bestått.");
            System.exit(0);
        } else {
            System.exit(1);
        }
    }
}