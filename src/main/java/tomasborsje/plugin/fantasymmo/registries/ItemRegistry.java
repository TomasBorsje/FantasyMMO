package tomasborsje.plugin.fantasymmo.registries;

import net.minecraft.world.item.Items;
import tomasborsje.plugin.fantasymmo.core.GenericItem;
import tomasborsje.plugin.fantasymmo.core.Registry;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.util.CustomColors;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.items.*;
import tomasborsje.plugin.fantasymmo.recipes.RecipeRegistry;
import tomasborsje.plugin.fantasymmo.recipes.RecipeScrollItem;

public class ItemRegistry {
    public static final Registry<ICustomItem> ITEMS = new Registry<>();
    public static final ICustomItem SLIMEBALL = ITEMS.register(new GenericItem("SLIMEBALL", "Slimeball", Items.SLIME_BALL, Rarity.JUNK, ItemUtil.Value(0,0,3), "A ball of slime.\nGross..."));

    // Usable items
    public static final ICustomItem NOVICE_WAND = ITEMS.register(new NoviceWand());
    public static final ICustomItem NOVICE_SPELLBLADE = ITEMS.register(new NoviceSpellblade());
    public static final ICustomItem MISTWEAVE_ROBE = ITEMS.register(new MistweaveRobe());
    public static final ICustomItem RECIPE_SCROLL_SLIME_TO_TRAINING_WAND = ITEMS.register(new RecipeScrollItem(RecipeRegistry.SLIME_TO_TRAINING_WAND, Rarity.LEGENDARY));

    public static final ICustomItem SLIME_JELLY = ITEMS.register(new BasicHealthFoodItem("SLIME_JELLY", "Slime Jelly","Jelly made from forest slimes.", Rarity.COMMON, Items.SLIME_BALL, ItemUtil.Value(0,0,6), 10, 5));

    public static final ICustomItem SLIME_SLUSHY = ITEMS.register(new BasicManaPotionItem("SLIME_SLUSHY", "Slime Slushy","A cold and slimy slushy.", CustomColors.SLIME_COLOR, Rarity.COMMON, ItemUtil.Value(0,0,6), 10, 5));
}