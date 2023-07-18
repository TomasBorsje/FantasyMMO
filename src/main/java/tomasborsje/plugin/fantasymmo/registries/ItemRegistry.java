package tomasborsje.plugin.fantasymmo.registries;

import org.bukkit.Bukkit;
import tomasborsje.plugin.fantasymmo.content.items.holders.*;
import tomasborsje.plugin.fantasymmo.core.Registry;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;

/**
 * Stores custom items in a private registry.
 * Other holder classes call on this to register their respective categories of items.
 * @see CustomItems
 * @see CustomWeapons
 */
public class ItemRegistry {
    // Class that actually holds all the items
    public static final Registry<ICustomItem> ITEMS = new Registry<>();

    // Awful static class references required for the specific item holders to be compiled
    static {
        try {
            Class.forName(CustomArrows.class.getCanonicalName());
            Class.forName(CustomEquipment.class.getName());
            Class.forName(CustomFoods.class.getName());
            Class.forName(CustomItems.class.getName());
            Class.forName(CustomRecipes.class.getName());
            Class.forName(CustomRecipeScrolls.class.getName());
            Class.forName(CustomWeapons.class.getName());
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().warning("Yo, "+e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
