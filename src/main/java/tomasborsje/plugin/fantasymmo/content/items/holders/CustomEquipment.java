package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.content.items.equipment.MistweaveRobe;
import tomasborsje.plugin.fantasymmo.content.items.equipment.SolarPhoenixHood;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.registries.RegistryEntry;

/**
 * Holds all custom equipment items.
 */
public class CustomEquipment {
    public static final RegistryEntry<ICustomItem> MISTWEAVE_ROBE = ItemRegistry.ITEMS.register(new MistweaveRobe());
    public static final RegistryEntry<ICustomItem> SOLAR_PHOENIX_HOOD = ItemRegistry.ITEMS.register(new SolarPhoenixHood());
}
