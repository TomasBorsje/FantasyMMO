package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

import java.util.HashMap;

public class PlayerDataHandler {
    public static final PlayerDataHandler instance = new PlayerDataHandler();
    public HashMap<String, PlayerData> playerData = new HashMap<>();

    private PlayerDataHandler() {}

    public PlayerData getPlayerData(Player player) {
        if(!playerData.containsKey(player.getDisplayName())) {
            throw new RuntimeException("Player info for " + player.getDisplayName() + " does not exist!");
        }
        return playerData.get(player.getDisplayName());
    }

    public void addPlayerInfo(Player player) {
        // TODO: Load player data from database
        // For now we just make a new one though
        playerData.put(player.getDisplayName(), new PlayerData(player));
    }

    public boolean hasPlayerInfo(Player player) {
        return playerData.containsKey(player.getDisplayName());
    }
}

