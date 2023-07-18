package tomasborsje.plugin.fantasymmo.registries;

public class RegistryEntry<T> {
    private final T entry;
    private final String id;

    private RegistryEntry() {
        this.id = null;
        this.entry = null;
    }

    public RegistryEntry(T entry, String id) {
        this.entry = entry;
        this.id = id;
    }

    public T get() {
        return entry;
    }
}
