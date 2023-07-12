package tomasborsje.plugin.fantasymmo.recipes;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;
import tomasborsje.plugin.fantasymmo.core.interfaces.IHasId;
import tomasborsje.plugin.fantasymmo.core.enums.ProfessionType;

public interface ICustomRecipe extends IHasId {
    public ItemStack getResult();
    public ICustomItem getOutputItem();
    public boolean canCraft(Inventory inventory);
    public IIngredient[] getIngredients();
    public ItemStack craft(Inventory inventory);
    public ProfessionType getProfessionType();
}
