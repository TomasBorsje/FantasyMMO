package tomasborsje.plugin.fantasymmo.core.interfaces;

import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

public interface IUsable {
    public default boolean rightClick(PlayerData playerData, ItemStack item) {return false;};
    public default boolean leftClick(PlayerData playerData, ItemStack item) {return false;};
    public default int getTickCooldown() {return 20;}
    public default boolean isConsumable() { return false; }
    public default boolean allowDefaultUse() { return false; }
    public default String getLeftClickDescription() {
        return "";
    };
    public default String getRightClickDescription() {
        return "";
    };
}
