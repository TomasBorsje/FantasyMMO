package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadListener implements Listener {
    @EventHandler
    public void OnWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        // Set game rules
        setWorldDefaults(world);
        // Destroy all armor stands
        for(Entity entity : world.getEntities()) {
            if(entity instanceof CraftArmorStand armorStand) {
                if(armorStand.isCustomNameVisible()) {
                    entity.remove();
                }
            }
        }
    }
    void setWorldDefaults(World world) {
        world.setTime(0);
        world.setSpawnLocation(-8, 69, 7);
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
