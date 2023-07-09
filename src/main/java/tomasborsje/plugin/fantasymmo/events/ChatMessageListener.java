package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.TooltipUtil;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;

public class ChatMessageListener implements Listener {
    @EventHandler
    public void onPlayerSendMessage(AsyncPlayerChatEvent event) {
        // Add the player's level as a prefix to their message
        String msg = event.getMessage();
        // Get player's playerdata
        PlayerData playerData = PlayerHandler.instance.getPlayerData(event.getPlayer());
        // Add the player's level as a prefix to their message
        event.setFormat(TooltipUtil.getLevelDisplay(playerData.getLevel()) + event.getPlayer().getDisplayName() + ": " + ChatColor.GREEN + msg);
    }
}
