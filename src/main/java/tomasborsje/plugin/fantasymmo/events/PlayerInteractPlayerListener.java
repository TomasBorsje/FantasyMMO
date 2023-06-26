package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

public class PlayerInteractPlayerListener implements Listener {
    @EventHandler
    public void OnPlayerInteractPlayer(PlayerInteractEntityEvent event) {

        // Only handle players right-clicking players
        if(event.getHand() != EquipmentSlot.OFF_HAND || !(event.getRightClicked() instanceof Player)) { return; }

        event.setCancelled(true);

        // Store id of what was clicked
        int clickedEntityId = event.getRightClicked().getEntityId();

        // Get player data of clicker
        PlayerData playerData = PlayerHandler.instance.getPlayerData(event.getPlayer());

        // If the right-clicked entity is a custom NPC, interact with the NPC
        if(EntityHandler.instance.hasNPC(clickedEntityId)) {
            EntityHandler.instance.getNPC(clickedEntityId).interact(playerData);
        }
        else {
            // Otherwise the clicked player is a real player
            // TODO: Open profile viewer, etc.
        }
    }
}
