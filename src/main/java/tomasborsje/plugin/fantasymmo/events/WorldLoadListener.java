package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadListener implements Listener {
    @EventHandler
    public void OnWorldLoad(WorldLoadEvent event) {
        Bukkit.getLogger().info("Setting defaults on world load...");
        World world = event.getWorld();
        // Set game rules
        setWorldDefaults(world);
        // Remove ALL entities, or else we get leftover entities from the last time the server was running
        // that are no longer stored in the custom NPC handler
        for(Entity entity : world.getEntities()) {
            entity.remove();
        }
    }
    void setWorldDefaults(World world) {
        world.setTime(6000); // Noon
        world.setSpawnLocation(21, 88, -10);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        world.setGameRule(GameRule.DO_MOB_LOOT, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.DO_TILE_DROPS, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
    }
}
