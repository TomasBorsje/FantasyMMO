package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.content.items.equipment.MistweaveRobe;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

/**
 * Holds all custom equipment items.
 */
public class CustomEquipment {
    public static final ICustomItem MISTWEAVE_ROBE = ItemRegistry.ITEMS.register(new MistweaveRobe());
}
