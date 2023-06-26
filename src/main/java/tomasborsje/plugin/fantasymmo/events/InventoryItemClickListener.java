package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.guis.DenyInvHolder;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

/**
 * Prevents movement of items from custom inventories like
 * NPC menus, dialogs, etc.
 */
public class InventoryItemClickListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void InventoryItemClickListener(InventoryClickEvent event) {
        // Don't handle null invs
        if(event.getClickedInventory() == null || event.getClickedInventory().getHolder() == null) {
            return;
        }

        // Cancel all inventory move events to or from custom inventories
        if(event.getClickedInventory().getHolder() instanceof DenyInvHolder) {
            // Cancel the event
            event.setCancelled(true);
            // Get player data
            PlayerData playerData = PlayerHandler.instance.getPlayerData(event.getWhoClicked().getName());
            // If the player is in a GUI, pass in the slot click
            if(playerData.hasGUIOpen()) {
                playerData.getCurrentGUI().onClickSlot(event.getSlot());
            }
        }
    }

}
