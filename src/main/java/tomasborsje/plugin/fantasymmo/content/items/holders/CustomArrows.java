package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.content.items.arrows.SimpleArrow;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

/**
 * Holds all custom arrow items.
 */
public class CustomArrows {
    public static final ICustomItem SIMPLE_ARROW = ItemRegistry.Register(new SimpleArrow());
}
