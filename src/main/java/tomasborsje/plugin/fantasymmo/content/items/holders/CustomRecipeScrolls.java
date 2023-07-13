package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.core.enums.Rarity;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.recipes.RecipeScrollItem;
import tomasborsje.plugin.fantasymmo.registries.ItemRegistry;

/**
 * Holds all custom recipe scroll items.
 */
public class CustomRecipeScrolls {
    public static final ICustomItem RECIPE_SCROLL_SLIME_TO_TRAINING_WAND =
            ItemRegistry.Register(new RecipeScrollItem(CustomRecipes.SLIME_TO_TRAINING_WAND, Rarity.LEGENDARY));
}
