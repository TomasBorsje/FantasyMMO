package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

/**
 * Handles custom block break events, such as profession nodes.
 */
public class BlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void OnPlayerBreakBlock(BlockBreakEvent event) {
        event.setCancelled(true);

        Vector blockPos = event.getBlock().getLocation().toVector();
        Player player = event.getPlayer();

        // Check if there is a profession node at this position
        // TODO:
    }
}
