package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.content.items.weapons.NoviceLongbow;
import tomasborsje.plugin.fantasymmo.content.items.weapons.NoviceSpellblade;
import tomasborsje.plugin.fantasymmo.content.items.weapons.NoviceWand;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

/**
 * Holds all custom weapon items.
 */
public class CustomWeapons {
    public static final ICustomItem NOVICE_WAND = ItemRegistry.Register(new NoviceWand());
    public static final ICustomItem NOVICE_SPELLBLADE = ItemRegistry.Register(new NoviceSpellblade());
    public static final ICustomItem NOVICE_LONGBOW = ItemRegistry.Register(new NoviceLongbow());
}
