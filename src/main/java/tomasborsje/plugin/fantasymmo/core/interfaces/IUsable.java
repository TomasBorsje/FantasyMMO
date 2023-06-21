package tomasborsje.plugin.fantasymmo.core.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IUsable {
    public boolean rightClick(Player player, ItemStack item);
    public boolean leftClick(Player player, ItemStack item);
    public default String getLeftClickDescription() {
        return "";
    };
    public default String getRightClickDescription() {
        return "";
    };
}
