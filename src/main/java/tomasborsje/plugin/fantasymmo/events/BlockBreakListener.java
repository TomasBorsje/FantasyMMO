package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.GameMode;
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
        // Allow block breaking in creative
        if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // Don't allow vanilla block breaking
        event.setCancelled(true);

        Vector blockPos = event.getBlock().getLocation().toVector();
        Player player = event.getPlayer();

        // TODO: Check if there is a profession node at this position
    }
}
