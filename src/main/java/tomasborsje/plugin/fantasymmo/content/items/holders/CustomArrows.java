package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.content.items.arrows.SimpleArrow;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.registries.RegistryEntry;

/**
 * Holds all custom arrow items.
 */
public class CustomArrows {
    public static final RegistryEntry<ICustomItem> SIMPLE_ARROW = ItemRegistry.ITEMS.register(new SimpleArrow());
}
