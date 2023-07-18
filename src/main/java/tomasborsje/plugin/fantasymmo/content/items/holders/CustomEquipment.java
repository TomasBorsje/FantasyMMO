package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.content.items.equipment.*;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.registries.RegistryEntry;

/**
 * Holds all custom equipment items.
 */
public class CustomEquipment {
    public static final RegistryEntry<ICustomItem> MISTWEAVE_ROBE = ItemRegistry.ITEMS.register(new MistweaveRobe());
    public static final RegistryEntry<ICustomItem> SOLAR_PHOENIX_HOOD = ItemRegistry.ITEMS.register(new SolarPhoenixHood());
    public static final RegistryEntry<ICustomItem> SOLAR_PHOENIX_CHESTPLATE = ItemRegistry.ITEMS.register(new SolarPhoenixChestplate());
    public static final RegistryEntry<ICustomItem> SOLAR_PHOENIX_LEGGINGS = ItemRegistry.ITEMS.register(new SolarPhoenixLeggings());
    public static final RegistryEntry<ICustomItem> SOLAR_PHOENIX_BOOTS = ItemRegistry.ITEMS.register(new SolarPhoenixBoots());
}
