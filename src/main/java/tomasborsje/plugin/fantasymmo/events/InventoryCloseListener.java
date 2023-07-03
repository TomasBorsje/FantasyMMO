package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.guis.DenyInvHolder;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void InventoryCloseListener(InventoryCloseEvent event) {
        // If the inventory was a custom inventory
        if (event.getInventory().getHolder() instanceof DenyInvHolder && event.getPlayer() instanceof Player player) {
            // Get player data
            PlayerData playerData = PlayerHandler.instance.getPlayerData(player);
            // Set the player's current GUI to null

            playerData.closeGUI();
        }
    }
}
