package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import tomasborsje.plugin.fantasymmo.guis.DenyInvHolder;

public class InventoryOpenListener implements Listener {
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if(!(event.getInventory().getHolder() instanceof DenyInvHolder)) {
            event.setCancelled(true);
        }
    }
}
