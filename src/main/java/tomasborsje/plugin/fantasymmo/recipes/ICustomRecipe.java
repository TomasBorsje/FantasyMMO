package tomasborsje.plugin.fantasymmo.recipes;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasId;

public interface ICustomRecipe extends IHasId {
    public ItemStack getResult();
    public boolean canCraft(Inventory inventory);
    public IIngredient[] getIngredients();
    public ItemStack craft(Inventory inventory);
}
