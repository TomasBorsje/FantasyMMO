package tomasborsje.plugin.fantasymmo.recipes;

import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;

/**
 * Represents a crafting ingredient that checks if an item has a specific ITEM_ID NBT tag.
 */
public class GenericIngredient implements IIngredient {
    private final String itemID;
    private final String ingredientName;
    private final int requiredCount;

    /**
     * Creates a new ingredient requiring 1 item with a specific ID.
     *
     * @param itemID The ID of the item.
     * @param ingredientName The name of the ingredient.
     */
    private GenericIngredient(String itemID, String ingredientName) {
        this.itemID = itemID;
        this.ingredientName = ingredientName;
        this.requiredCount = 1;
    }

    public GenericIngredient withCount(int count) {
        return new GenericIngredient(itemID, ingredientName, count);
    }

    /**
     * Creates a new ingredient requiring a specific amount of items with a specific ID.
     *
     * @param itemID The ID of the item.
     * @param ingredientName The name of the ingredient.
     * @param requiredCount The amount of items required.
     */
    private GenericIngredient(String itemID, String ingredientName, int requiredCount) {
        this.itemID = itemID;
        this.ingredientName = ingredientName;
        this.requiredCount = requiredCount;
    }

    public GenericIngredient(ICustomItem item, int requiredCount) {
        this.itemID = item.getCustomId();
        this.ingredientName = TooltipUtil.getItemDisplayName(item);
        this.requiredCount = requiredCount;
    }
    public GenericIngredient(ICustomItem item) {
        this.itemID = item.getCustomId();
        this.ingredientName = TooltipUtil.getItemDisplayName(item);
        this.requiredCount = 1;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return matches(CraftItemStack.asNMSCopy(stack));
    }

    @Override
    public boolean matches(net.minecraft.world.item.ItemStack stack) {
        // Get NBT and check if ITEM ID exists and matches
        return stack.getCount() >= this.requiredCount
                && stack.hasTag()
                && stack.getTag().contains("ITEM_ID")
                && stack.getTag().getString("ITEM_ID").equals(itemID);
    }

    @Override
    public boolean matches(ICustomItem item, int count) {
        return count >= this.requiredCount && item.getCustomId().equals(itemID);
    }

    @Override
    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public int getRequiredCount() {
        return requiredCount;
    }
}
