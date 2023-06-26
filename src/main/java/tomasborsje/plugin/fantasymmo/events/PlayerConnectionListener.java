package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

public class PlayerConnectionListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Load player data as they join
        PlayerHandler.instance.loadPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        // If the player contains a custom character, don't handle them
        // This means they are a custom NPC
        // Save player data as they leave
        PlayerHandler.instance.savePlayerData(event.getPlayer());
    }

    private boolean isCustomNpc(Player player) {
        return player.getName().contains("§");
    }
}
