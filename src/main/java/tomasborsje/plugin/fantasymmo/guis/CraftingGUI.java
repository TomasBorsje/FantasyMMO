package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.CustomHeads;
import tomasborsje.plugin.fantasymmo.recipes.ICustomRecipe;
import tomasborsje.plugin.fantasymmo.recipes.IIngredient;
import tomasborsje.plugin.fantasymmo.recipes.RecipeRegistry;

import java.util.ArrayList;
import java.util.List;

public class CraftingGUI extends CustomGUI {
    private static final ItemStack arrow = CustomHeads.GetCraftingArrowHead();
    List<ICustomRecipe> knownRecipes;
    private final static int LEFT_INGREDIENT_COLUMN = 1;
    private final static int LEFT_ARROW_COLUMN = 2;
    private final static int LEFT_RESULT_COLUMN = 3;
    private final static int RIGHT_INGREDIENT_COLUMN = 5;
    private final static int RIGHT_ARROW_COLUMN = 6;
    private final static int RIGHT_RESULT_COLUMN = 7;

    public CraftingGUI(PlayerData playerData) {
        super(playerData, 54, "Crafting Recipes");
        // Grab LEARNED/known recipes
        this.knownRecipes = RecipeRegistry.GetKnownRecipes(playerData);
    }

    @Override
    protected Inventory renderInventory() {
        super.renderInventory();

        for(int i = 0; i < knownRecipes.size() && i < 8; i++) {
            // Slot index offset for each row
            int rowOffset = (i+1) * 9;
            // Get recipe
            ICustomRecipe recipe = knownRecipes.get(i);
            // Create Paper itemstack to represent ingredients
            ItemStack ingredientDisplay = getIngredientsDisplay(recipe.getIngredients());
            // Get output result to display
            ItemStack result = recipe.getResult();
            // See if we can craft it
            boolean canCraft = recipe.canCraft(this.playerData.player.getInventory());
            // Add click to craft message to result
            ItemMeta meta = result.getItemMeta();
            List<String> lore = meta.getLore();
            if(canCraft) {
                lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "Click to craft!");
            } else {
                lore.add(ChatColor.RED + "" + ChatColor.BOLD + "You don't have the required materials!");
            }
            meta.setLore(lore);
            result.setItemMeta(meta);

            // Set slots to display ingredients and result
            if(i < 4) {
                display.setItem(LEFT_INGREDIENT_COLUMN + rowOffset, ingredientDisplay);
                display.setItem(LEFT_ARROW_COLUMN + rowOffset, arrow);
                display.setItem(LEFT_RESULT_COLUMN + rowOffset, result);
            } else {
                display.setItem(RIGHT_INGREDIENT_COLUMN + rowOffset, ingredientDisplay);
                display.setItem(RIGHT_ARROW_COLUMN + rowOffset, arrow);
                display.setItem(RIGHT_RESULT_COLUMN + rowOffset, result);
            }
        }
        return display;
    }

    private ItemStack getIngredientsDisplay(IIngredient[] ingredients) {
        ItemStack display = new ItemStack(Material.PAPER);
        ItemMeta meta = display.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Required Materials");
        ArrayList<String> lore = new ArrayList<>();
        for(IIngredient ingredient : ingredients) {
            lore.add(ChatColor.WHITE+""+ingredient.getRequiredCount()+"x " + ingredient.getIngredientName());
        }
        meta.setLore(lore);
        display.setItemMeta(meta);
        return display;
    }

    @Override
    public void onClickSlot(int slot) {
        // Craft items if player clicks on result slot
        // If left result column clicked
        int recipeIndex;
        if(slot % 9 == LEFT_RESULT_COLUMN) {
            // Get row
            recipeIndex = (int) (slot / 9f);
        }
        else if (slot % 9 == RIGHT_RESULT_COLUMN) {
            // Get row
            recipeIndex = (int) (slot / 9f) + 4;
        }
        else {
            return;
        }
        // We're crafting a recipe
        // Get recipe
        ICustomRecipe recipe = knownRecipes.get(recipeIndex-1);
        Inventory playerInv = playerData.player.getInventory();

        // If we can't craft, don't
        if(!recipe.canCraft(playerInv)) {
            return;
        }

        // Craft and set in cursor slot
        ItemStack output = recipe.craft(playerInv);
        playerData.giveItem(output);

        playerData.player.sendMessage(ChatColor.LIGHT_PURPLE + "You crafted " + output.getItemMeta().getDisplayName() + ChatColor.LIGHT_PURPLE + "!");

        // Re-Render, so recipe outputs update
        playerData.openGUI(this);
    }
}
