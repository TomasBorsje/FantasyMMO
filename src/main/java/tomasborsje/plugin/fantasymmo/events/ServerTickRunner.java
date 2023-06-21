package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tomasborsje.plugin.fantasymmo.FantasyMMO;
import tomasborsje.plugin.fantasymmo.handlers.NPCHandler;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.handlers.PlayerDataHandler;

import java.util.List;

public class ServerTickRunner extends BukkitRunnable {

    @Override
    public void run() {
        World world = FantasyMMO.Plugin.getServer().getWorlds().get(0);
        if(world == null) { return; }

        // Get all players and store their data
        List<Player> players = world.getPlayers();

        // Tick players and calculate their stats
        PlayerDataHandler playerDataHandler = PlayerDataHandler.instance;
        for(Player player : players) {
            // Make sure each player has data stored
            if(!playerDataHandler.hasPlayerInfo(player)) {
                // Load their data if they're new
                // TODO: Load from database
                playerDataHandler.addPlayerInfo(player);
            }
            PlayerData playerData = playerDataHandler.getPlayerData(player);
            // Recalc stats
            playerData.recalculateStats();
            // Tick!
            playerData.tick();
        }

        // Tick projectiles
        ProjectileHandler.instance.tick();

        // Tick NPCs
        NPCHandler.instance.tick();
    }
}