package tomasborsje.plugin.fantasymmo.core.registries;

import net.minecraft.world.item.Items;
import tomasborsje.plugin.fantasymmo.core.GenericItem;
import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.util.ItemUtil;
import tomasborsje.plugin.fantasymmo.items.MistweaveRobe;
import tomasborsje.plugin.fantasymmo.items.TrainingWand;
import tomasborsje.plugin.fantasymmo.recipes.RecipeRegistry;
import tomasborsje.plugin.fantasymmo.recipes.RecipeScrollItem;

public class ItemRegistry {
    public static final Registry<ICustomItem> ITEMS = new Registry<>();
    public static final ICustomItem SLIMEBALL = ITEMS.register(new GenericItem("SLIMEBALL", "Slimeball", Items.SLIME_BALL, Rarity.JUNK, ItemUtil.Value(0,0,3), "A ball of slime.\nGross..."));

    // Usable items
    public static final ICustomItem TRAINING_WAND = ITEMS.register(new TrainingWand());
    public static final ICustomItem MISTWEAVE_ROBE = ITEMS.register(new MistweaveRobe());
    public static final ICustomItem RECIPE_SCROLL_SLIME_TO_TRAINING_WAND = ITEMS.register(new RecipeScrollItem(RecipeRegistry.SLIME_TO_TRAINING_WAND, Rarity.LEGENDARY));
}
