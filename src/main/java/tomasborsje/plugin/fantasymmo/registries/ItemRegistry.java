package tomasborsje.plugin.fantasymmo.registries;

import tomasborsje.plugin.fantasymmo.content.items.holders.CustomItems;
import tomasborsje.plugin.fantasymmo.content.items.holders.CustomWeapons;
import tomasborsje.plugin.fantasymmo.core.Registry;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;

/**
 * Stores custom items in a private registry.
 * Other holder classes call on this to register their respective categories of items.
 * @see CustomItems
 * @see CustomWeapons
 */
public class ItemRegistry {
    private static final Registry<ICustomItem> ITEMS = new Registry<>();
    public static ICustomItem Register(ICustomItem item) {
        ITEMS.register(item);
        return item;
    }
    public static ICustomItem Get(String id) {
        return ITEMS.get(id);
    }

    public static boolean Exists(String id) {
        return ITEMS.exists(id);
    }
}
