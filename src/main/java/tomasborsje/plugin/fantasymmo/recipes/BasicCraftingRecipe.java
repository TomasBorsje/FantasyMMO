package tomasborsje.plugin.fantasymmo.recipes;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.ProfessionType;

public class BasicCraftingRecipe implements ICustomRecipe {
    private final IIngredient[] ingredients;
    private final ICustomItem output;
    private final ProfessionType type;
    private final String id;

    public BasicCraftingRecipe(String id, ProfessionType professionType, ICustomItem output, IIngredient... ingredients) {
        this.id = id;
        this.output = output;
        this.type = professionType;
        this.ingredients = ingredients;
    }

    @Override
    public ICustomItem getOutputItem() {
        return output;
    }

    @Override
    public ItemStack getResult() {
        return output.createStack();
    }

    /**
     * Checks if the items in the given inventory match the recipe.
     */
    public boolean canCraft(Inventory inventory) {
        // TODO: Combine counts of stacks with the same ITEM_ID

        // Check if each ingredient is met
        for(IIngredient ingredient : ingredients) {
            boolean ingredientSatisfied = false;

            // Check if any of the items in the inventory match the ingredient
            for(ItemStack stack : inventory.getContents()) {
                if(ingredient.matches(stack)) {
                    ingredientSatisfied = true;
                    break;
                }
            }

            // If no item matched the ingredient, the recipe is not satisfied
            if(!ingredientSatisfied) {
                return false;
            }
        }

        // All ingredients met, recipe craftable
        return true;
    }

    /**
     * Crafts an item, removing the ingredients from the inventory
     * and then adding the result to the mouse cursor.
     * @param inventory
     */
    public ItemStack craft(Inventory inventory) {
        for(IIngredient ingredient : ingredients) {
            // Remove the ingredient from the inventory
            for(ItemStack stack : inventory) {
                if(ingredient.matches(stack)) {
                    stack.setAmount(stack.getAmount() - ingredient.getRequiredCount());
                    break;
                }
            }
        }
        return getResult();
    }

    @Override
    public ProfessionType getProfessionType() {
        return type;
    }

    @Override
    public IIngredient[] getIngredients() {
        return ingredients;
    }

    @Override
    public String getCustomId() {
        return id;
    }
}
