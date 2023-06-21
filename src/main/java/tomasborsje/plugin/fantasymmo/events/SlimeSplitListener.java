package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SlimeSplitEvent;

public class SlimeSplitListener implements Listener {
    @EventHandler
    public void OnSlimeSplitEvent(SlimeSplitEvent event) {
        // No vanilla slime splitting allowed
        event.setCancelled(true);
    }
}
