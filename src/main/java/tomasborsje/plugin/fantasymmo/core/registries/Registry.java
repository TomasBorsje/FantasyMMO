package tomasborsje.plugin.fantasymmo.core.registries;

import tomasborsje.plugin.fantasymmo.core.interfaces.IHasId;

import java.util.HashMap;

/**
 * Registry class that holds entries of type IHasId.
 * @param <T> Any type that has custom ids.
 */
public class Registry<T extends IHasId> {
    /**
     * The registry that holds all entries.
     */
    private final HashMap<String, T> registry = new HashMap<>();

    /**
     * Registers an entry with a given id and IHasId instance.
     * @param id The id to register the entry with.
     * @param entry The entry to register.
     */
    public T register(String id, T entry) {
        if(registry.containsKey(id)) {
            throw new IllegalArgumentException("Entry with id " + id + " is already registered!");
        }
        registry.put(id, entry);
        return registry.get(id);
    }

    /**
     * Registers an entry with the id of the IHasId instance.
     * @param entry The entry to register.
     */
    public T register(T entry) {
        return register(entry.getCustomId(), entry);
    }

    /**
     * Returns whether an entry with the given id is registered.
     * @param id The id to check.
     * @return Whether an entry with the given id is registered.
     */
    public boolean exists(String id) {
        return registry.containsKey(id);
    }

    /**
     * Get an entry by its id.
     * @param id The id of the entry to get.
     */
    public T get(String id) {
        if(!registry.containsKey(id)) {
            throw new IllegalArgumentException("Entry with id " + id + " is not registered!");
        }
        return registry.get(id);
    }
}