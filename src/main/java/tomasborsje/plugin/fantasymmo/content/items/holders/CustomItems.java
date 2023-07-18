package tomasborsje.plugin.fantasymmo.content.items.holders;

import net.minecraft.world.item.Items;
import tomasborsje.plugin.fantasymmo.core.GenericItem;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.registries.RegistryEntry;

/**
 * Holds all custom generic items.
 * These are items that don't do anything on their own.
 */
public class CustomItems {
    public static final RegistryEntry<ICustomItem> SLIMEBALL = ItemRegistry.ITEMS.register(new GenericItem("SLIMEBALL", "Slimeball", Items.SLIME_BALL, Rarity.JUNK, ItemUtil.Value(0, 0, 3), "A ball of slime.\nGross..."));

}
