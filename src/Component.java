/**
 * Representerer en systemkomponent (f.eks. Maskinvare, Programvare eller Elektrisk).
 * Dette gjør at vi kan gruppere krav under spesifikke deler av systemet.
 */
public class Component {
    private String name;
    private String category;

    public Component(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return name + " (" + category + ")";
    }
}