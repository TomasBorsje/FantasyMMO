package tomasborsje.plugin.fantasymmo.content.items.holders;

import net.minecraft.world.item.Items;
import tomasborsje.plugin.fantasymmo.core.BasicHealthFoodItem;
import tomasborsje.plugin.fantasymmo.core.BasicManaPotionItem;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.util.CustomColors;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.registries.RegistryEntry;

/**
 * Holds all custom food items.
 */
public class CustomFoods {
    public static final RegistryEntry<ICustomItem> SLIME_JELLY = ItemRegistry.ITEMS.register(new BasicHealthFoodItem("SLIME_JELLY", "Slime Jelly","Jelly made from wild slimes.", Rarity.COMMON, Items.SLIME_BALL, ItemUtil.Value(0,0,6), 10, 5));
    public static final RegistryEntry<ICustomItem> SLIME_SLUSHY = ItemRegistry.ITEMS.register(new BasicManaPotionItem("SLIME_SLUSHY", "Slime Slushy","A cold and slimy slushy.", CustomColors.SLIME_COLOR, Rarity.COMMON, ItemUtil.Value(0,0,6), 10, 5));
}
