package tomasborsje.plugin.fantasymmo.recipes;

import org.bukkit.inventory.Inventory;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.ProfessionType;
import tomasborsje.plugin.fantasymmo.core.registries.ItemRegistry;
import tomasborsje.plugin.fantasymmo.core.registries.Registry;

import java.util.ArrayList;
import java.util.List;

// TODO: Split into a registry for each profession
// Maybe make a new registry class that splits recipes by type internally?
public class RecipeRegistry {
    public static final Registry<ICustomRecipe> RECIPES = new Registry<>();

    // TODO: Profession specific recipes
    public static List<ICustomRecipe> GetCraftableRecipes(Inventory inv) {
        ArrayList<ICustomRecipe> craftableRecipes = new ArrayList<>();

        // For each recipe, if it can be crafted, save it
        for(var recipe : RECIPES.getAllValues()) {
            if(recipe.canCraft(inv)) {
                craftableRecipes.add(recipe);
            }
        }

        return craftableRecipes;
    }

    /**
     * Returns a list of Recipes that the player knows.
     * @param data The player to get recipes for.
     * @return A list of recipes that the player knows.
     */
    public static List<ICustomRecipe> GetKnownRecipes(PlayerData data) {
        ArrayList<ICustomRecipe> knownRecipes = new ArrayList<>();

        // For each recipe, if the player knows it by ID, save it
        for(String recipeId : data.knownRecipeIds) {
            if(RECIPES.exists(recipeId)) {
                knownRecipes.add(RECIPES.get(recipeId));
            }
        }

        return knownRecipes;
    }

    public static final ICustomRecipe SLIME_TO_TRAINING_WAND =
            RECIPES.register(new BasicCraftingRecipe("SLIME_TO_TRAINING_WAND", ProfessionType.BLACKSMITHING, ItemRegistry.TRAINING_WAND, Ingredients.SLIMEBALL.withCount(5)));

    public static final ICustomRecipe TRAINING_WAND_TO_ROBE =
            RECIPES.register(new BasicCraftingRecipe("TRAINING_WAND_TO_ROBE", ProfessionType.BLACKSMITHING, ItemRegistry.MISTWEAVE_ROBE, Ingredients.TRAINING_WAND));
}
