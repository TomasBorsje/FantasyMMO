package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.recipes.RecipeScrollItem;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.registries.RegistryEntry;

/**
 * Holds all custom recipe scroll items.
 */
public class CustomRecipeScrolls {
    public static final RegistryEntry<ICustomItem> RECIPE_SCROLL_SLIME_TO_TRAINING_WAND =
            ItemRegistry.ITEMS.register(new RecipeScrollItem(CustomRecipes.SLIME_TO_TRAINING_WAND, Rarity.LEGENDARY));
}
