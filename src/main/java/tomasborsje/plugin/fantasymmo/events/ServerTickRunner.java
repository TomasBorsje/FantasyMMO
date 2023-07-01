package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import tomasborsje.plugin.fantasymmo.FantasyMMO;
import tomasborsje.plugin.fantasymmo.handlers.EntitySpawningHandler;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;

public class ServerTickRunner extends BukkitRunnable {
    private static final int SAVE_INTERVAL = 20 * 60 * 5; // 5 minutes
    private int saveTimer = 0;
    @Override
    public void run() {
        World world = FantasyMMO.Plugin.getServer().getWorlds().get(0);
        if(world == null) { return; }

        // Tick players
        PlayerHandler.instance.tick(world);

        // Tick projectiles
        ProjectileHandler.instance.tick();

        // Tick NPC Spawnpoints
        EntitySpawningHandler.instance.tick(world);

        // Tick NPCs
        EntityHandler.instance.tick();

        // Save player data if needed
        saveTimer++;
        if(saveTimer >= SAVE_INTERVAL) {
            saveTimer = 0;
            if(!Bukkit.getOnlinePlayers().isEmpty()) {
                PlayerHandler.instance.saveAllPlayerData();
            }
        }
    }
}