package tomasborsje.plugin.fantasymmo.content.items.holders;

import tomasborsje.plugin.fantasymmo.core.enums.ProfessionType;
import tomasborsje.plugin.fantasymmo.recipes.BasicCraftingRecipe;
import tomasborsje.plugin.fantasymmo.recipes.ICustomRecipe;
import tomasborsje.plugin.fantasymmo.recipes.Ingredients;
import tomasborsje.plugin.fantasymmo.registries.RecipeRegistry;

/**
 * Holds all custom recipes.
 */
public class CustomRecipes {
    public static final ICustomRecipe SLIME_TO_TRAINING_WAND =
            RecipeRegistry.Register(new BasicCraftingRecipe("SLIME_TO_TRAINING_WAND", ProfessionType.BLACKSMITHING, CustomWeapons.NOVICE_WAND.get(), Ingredients.SLIMEBALL.withCount(5)));

    public static final ICustomRecipe TRAINING_WAND_TO_ROBE =
            RecipeRegistry.Register(new BasicCraftingRecipe("TRAINING_WAND_TO_ROBE", ProfessionType.BLACKSMITHING, CustomEquipment.MISTWEAVE_ROBE.get(), Ingredients.TRAINING_WAND));

}
