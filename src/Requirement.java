/**
 * Representerer et teknisk systemkrav i BridgeFlow-rammeverket.
 * Inkluderer valideringslogikk for å sikre dataintegritet før modellering.
 */
public class Requirement {
    private String id;
    private String name;
    private String priority;
    private Component ownerComponent;

    public Requirement(String id, String name, String priority, Component owner) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.ownerComponent = owner;
    }

    // Gettere for datatransformasjon
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPriority() { return priority; }
    public Component getOwnerComponent() { return ownerComponent; }

    /**
     * Validerer objektet basert på forretningsregler:
     * 1. ID kan ikke være tom.
     * 2. Navnet må være lenger enn 2 tegn for å være beskrivende.
     */
    public boolean isValid() {
        return id != null && !id.isEmpty() && 
               name != null && name.length() > 2 && 
               ownerComponent != null;
    }

    @Override
    public String toString() {
        return String.format("[Requirement %s] %s (Prio: %s) - Owner: %s", id, name, priority, ownerComponent);
    }
}