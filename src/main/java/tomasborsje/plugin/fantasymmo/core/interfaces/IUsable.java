package tomasborsje.plugin.fantasymmo.core.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IUsable {
    public default boolean rightClick(Player player, ItemStack item) {return false;};
    public default boolean leftClick(Player player, ItemStack item) {return false;};
    public default int getTickCooldown() {return 20;}
    public default boolean isConsumable() { return false; }
    public default String getLeftClickDescription() {
        return "";
    };
    public default String getRightClickDescription() {
        return "";
    };
}
