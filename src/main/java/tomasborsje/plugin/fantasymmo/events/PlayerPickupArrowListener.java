package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class PlayerPickupArrowListener implements Listener {
    @EventHandler
    public void onPlayerPickupArrow(PlayerPickupArrowEvent event) {
        // TODO: Maybe give back the correct arrow type?
        event.setCancelled(true);
    }
}
