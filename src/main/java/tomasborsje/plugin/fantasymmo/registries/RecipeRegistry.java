package tomasborsje.plugin.fantasymmo.registries;

import org.bukkit.inventory.Inventory;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.Registry;
import tomasborsje.plugin.fantasymmo.recipes.ICustomRecipe;

import java.util.ArrayList;
import java.util.List;

// TODO: Split into a registry for each profession
// Maybe make a new registry class that splits recipes by type internally?
public class RecipeRegistry {
    private static final Registry<ICustomRecipe> RECIPES = new Registry<>();

    // TODO: Profession specific recipes
    public static List<ICustomRecipe> GetCraftableRecipes(Inventory inv) {
        ArrayList<ICustomRecipe> craftableRecipes = new ArrayList<>();

        // For each recipe, if it can be crafted, save it
        for(var recipe : RECIPES.getAllValues()) {
            if(recipe.get().canCraft(inv)) {
                craftableRecipes.add(recipe.get());
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
                knownRecipes.add(RECIPES.get(recipeId).get());
            }
        }

        return knownRecipes;
    }

    /**
     * Registers a recipe to the registry.
     * @param recipe The recipe to register.
     * @return The recipe that was registered.
     */
    public static ICustomRecipe Register(ICustomRecipe recipe) {
        RECIPES.register(recipe);
        return recipe;
    }

    /**
     * Gets a recipe by ID.
     * @param id The ID of the recipe to get.
     * @return The recipe with the given ID.
     */
    public static ICustomRecipe Get(String id) {
        return RECIPES.get(id).get();
    }

    /**
     * Checks if a recipe exists in the registry.
     * @param id The ID of the recipe to check.
     * @return Whether or not the recipe exists.
     */
    public static boolean Exists(String id) {
        return RECIPES.exists(id);
    }
}
