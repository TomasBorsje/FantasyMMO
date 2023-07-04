package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

/**
 * Disables crafting of vanilla items in the inventory, crafting tables, etc.
 */
public class PlayerCraftListener implements Listener {
    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        event.setCancelled(true);
    }
}
