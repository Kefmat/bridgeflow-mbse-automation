public class RequirementTest {
    public static void main(String[] args) {
        int failedTests = 0;

        System.out.println(">>> Running Unit Tests for Requirement Class...");

        // Test 1: Gyldig krav
        Requirement req1 = new Requirement("REQ-001", "Fuel Tank Capacity", "High");
        if (!req1.isValid()) {
            System.err.println("[FAIL] Valid requirement was marked as invalid.");
            failedTests++;
        }

        // Test 2: Ugyldig krav (for kort navn)
        Requirement req2 = new Requirement("REQ-002", "AB", "Low");
        if (req2.isValid()) {
            System.err.println("[FAIL] Invalid requirement (short name) was marked as valid.");
            failedTests++;
        }

        // Test 3: Ugyldig krav (mangler ID)
        Requirement req3 = new Requirement("", "Wing Span", "Medium");
        if (req3.isValid()) {
            System.err.println("[FAIL] Invalid requirement (empty ID) was marked as valid.");
            failedTests++;
        }

        if (failedTests == 0) {
            System.out.println("[SUCCESS] All tests passed!");
            System.exit(0); // Avslutter med suksess
        } else {
            System.err.println("[CRITICAL] " + failedTests + " tests failed!");
            System.exit(1); // Avslutter med feilkode (viktig for pipelinen!)
        }
    }
}