package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.content.items.weapons.NoviceLongbow;
import tomasborsje.plugin.fantasymmo.content.items.weapons.NoviceSpellblade;
import tomasborsje.plugin.fantasymmo.content.items.weapons.NoviceWand;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.registries.RegistryEntry;

/**
 * Holds all custom weapon items.
 */
public class CustomWeapons {
    public static final RegistryEntry<ICustomItem> NOVICE_WAND = ItemRegistry.ITEMS.register(new NoviceWand());
    public static final RegistryEntry<ICustomItem> NOVICE_SPELLBLADE = ItemRegistry.ITEMS.register(new NoviceSpellblade());
    public static final RegistryEntry<ICustomItem> NOVICE_LONGBOW = ItemRegistry.ITEMS.register(new NoviceLongbow());
}
