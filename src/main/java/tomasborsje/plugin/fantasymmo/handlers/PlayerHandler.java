package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.World;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

import java.util.HashMap;
import java.util.List;

public class PlayerHandler {
    public static final PlayerHandler instance = new PlayerHandler();
    public HashMap<String, PlayerData> playerDataMap = new HashMap<>();

    private PlayerHandler() {}

    public void tick(World world) {
        // Get all players and store their data
        List<Player> players = world.getPlayers();

        // Tick players and calculate their stats
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            // Make sure each player has data stored
            if (!hasPlayerInfo(player)) {
                // Load their data if they're new
                // TODO: Load from database
                addPlayerInfo(player);
            }
            PlayerData playerData = getPlayerData(player);
            // Tick valid players
            if(playerData.isValid()) {
                playerData.tick();
            }
            else {
                // Remove players who have left
                playerDataMap.remove(playerData.getUsername());
            }
        }
    }

    public PlayerData getPlayerData(Player player) {
        if(!playerDataMap.containsKey(player.getDisplayName())) {
            throw new RuntimeException("Player info for " + player.getDisplayName() + " does not exist!");
        }
        return playerDataMap.get(player.getDisplayName());
    }

    public void addPlayerInfo(Player player) {
        // TODO: Load player data from database
        // For now we just make a new one though
        playerDataMap.put(player.getDisplayName(), new PlayerData(player));
    }

    public boolean hasPlayerInfo(Player player) {
        return playerDataMap.containsKey(player.getDisplayName());
    }
}

