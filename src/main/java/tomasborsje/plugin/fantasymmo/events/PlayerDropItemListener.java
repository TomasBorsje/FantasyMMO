package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Called when a player drops an item.
 * Used to stop the player from dropping the world map.
 */
public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void OnPlayerDropItem(org.bukkit.event.player.PlayerDropItemEvent event) {
        // Check if the item is the world map
        if(event.getItemDrop().getItemStack().getItemMeta() != null &&
                event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.GOLD+""+ChatColor.BOLD+"World Map")) {
            // Prevent the player from dropping
            event.setCancelled(true);
        }
    }
}
