package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

public class PlayerConnectionListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Load player data as they join
        PlayerData playerData = PlayerHandler.instance.loadPlayerData(event.getPlayer());

        // Replace login message
        String loginMessage = playerData.getChatDisplayName() + ChatColor.WHITE+" has joined the game";
        event.setJoinMessage(loginMessage);

        // Load custom NPCS for the player
        EntityHandler.instance.spawnNPCsForPlayer(event.getPlayer());
        // Trigger post login event
        playerData.postLogin();
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        // Get player level before they leave
        String name = PlayerHandler.instance.getPlayerData(event.getPlayer()).getChatDisplayName();

        // Save player data as they leave
        PlayerHandler.instance.unloadPlayer(event.getPlayer());

        // Replace disconnect message
        String loginMessage = name + ChatColor.WHITE + " has left the game";
        event.setQuitMessage(loginMessage);
    }
}
