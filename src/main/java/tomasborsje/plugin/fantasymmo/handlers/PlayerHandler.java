package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.FantasyMMO;
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
            // If this is a custom NPC, ignore.
            if(player.getName().contains("§")) {
                continue;
            }
            // Make sure each player has data stored
            PlayerData playerData = loadPlayerData(player);
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

    public PlayerData loadPlayerData(Player player) {
        if(!playerDataMap.containsKey(player.getName())) {
            // Load player data from database
            // TODO: Multithread or something so it doesn't block main thread
            PlayerData newData = FantasyMMO.databaseConnection.loadPlayerData(player);
            // Store new player data
            playerDataMap.put(player.getName(), newData);
        }
        return playerDataMap.get(player.getName());
    }

    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getName());
    }
    public PlayerData getPlayerData(String username) {
        if(!playerDataMap.containsKey(username)) {
            throw new IllegalArgumentException("No player data found for " + username);
        }
        return playerDataMap.get(username);
    }

    public boolean hasPlayerInfo(Player player) {
        return playerDataMap.containsKey(player.getName());
    }

    public void saveAllPlayerData() {
        // Iterate through all players and save their data to DB
        for(PlayerData playerData : playerDataMap.values()) {
            FantasyMMO.databaseConnection.savePlayerData(playerData);
        }

        Bukkit.getLogger().info("Saved all player data to DB.");
    }

    public void savePlayerData(Player player) {
        // Get player data
        PlayerData playerData = loadPlayerData(player);
        // Save player data
        FantasyMMO.databaseConnection.savePlayerData(playerData);
    }
}

