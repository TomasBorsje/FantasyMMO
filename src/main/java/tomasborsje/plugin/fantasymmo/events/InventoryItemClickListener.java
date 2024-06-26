package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    public static final int MAP_SLOT = 8;

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnInventoryItemClick(InventoryClickEvent event) {
        // Don't handle null invs
        if(event.getSlot() == -999 || event.getClickedInventory() == null || event.getClickedInventory().getHolder() == null) {
            return;
        }

        // If a player clicked inside their inventory, deny it if it's the map slot
        if(event.getClickedInventory().getHolder() instanceof Player) {
            if(event.getSlot() == MAP_SLOT) {
                event.setCancelled(true);
            }
        }

        // Cancel all inventory move events to or from custom inventories
        if(event.getClickedInventory().getHolder() instanceof DenyInvHolder) {
            // Cancel the event
            event.setCancelled(true);
            // Get player data
            PlayerData playerData = PlayerHandler.instance.getPlayerData(event.getWhoClicked().getName());
            // If the player is in a GUI, pass in the slot click if they have an empty hand
            if(playerData.hasGUIOpen() && (event.getCursor() == null || event.getCursor().getType() == Material.AIR)) {
                playerData.getCurrentGUI().onClickSlot(event.getSlot());
            }
        }
    }

}
