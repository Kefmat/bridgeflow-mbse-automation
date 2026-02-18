public class Requirement {
    private String id;
    private String name;
    private String priority;

    public Requirement(String id, String name, String priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    // Gettere som trengs for JSON-eksport
    public String getId() { return id; }
    public String getName() { return name; }

    public boolean isValid() {
        return id != null && !id.isEmpty() && name != null && name.length() > 2;
    }

    @Override
    public String toString() {
        return String.format("[Requirement %s] %s", id, name);
    }
}