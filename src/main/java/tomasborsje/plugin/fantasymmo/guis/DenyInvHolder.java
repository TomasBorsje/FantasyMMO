package tomasborsje.plugin.fantasymmo.guis;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class DenyInvHolder implements InventoryHolder {
    public static final DenyInvHolder INSTANCE = new DenyInvHolder();
    @Override
    public Inventory getInventory() {
        return null;
    }
}
