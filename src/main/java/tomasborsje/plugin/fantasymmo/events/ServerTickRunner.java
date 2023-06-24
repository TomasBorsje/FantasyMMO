package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import tomasborsje.plugin.fantasymmo.FantasyMMO;
import tomasborsje.plugin.fantasymmo.handlers.NPCHandler;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;

public class ServerTickRunner extends BukkitRunnable {

    @Override
    public void run() {
        World world = FantasyMMO.Plugin.getServer().getWorlds().get(0);
        if(world == null) { return; }

        // Tick players
        PlayerHandler.instance.tick(world);

        // Tick projectiles
        ProjectileHandler.instance.tick();

        // Tick NPCs
        NPCHandler.instance.tick();
    }
}