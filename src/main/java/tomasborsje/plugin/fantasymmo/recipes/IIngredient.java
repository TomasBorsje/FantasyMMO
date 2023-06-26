package tomasborsje.plugin.fantasymmo.recipes;

import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.interfaces.ICustomItem;

public interface IIngredient {
    public boolean matches(ItemStack stack);
    public boolean matches(net.minecraft.world.item.ItemStack stack);
    public boolean matches(ICustomItem item, int count);
    public String getIngredientName();
    public int getRequiredCount();
}