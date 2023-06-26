package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.entity.LivingEntity;
import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;

/**
 * Represents a spawn point for entities.
 * Will spawn a new entity if the current one is dead
 * for a certain amount of time.
 */
public class NPCSpawner {
    private final static int SPAWN_DELAY = 20 * 5; // 5 seconds
    private int spawnTimer = 0;
    private final String npcId;
    private LivingEntity nmsEntity;
    private final Location spawnLoc;

    public NPCSpawner(String npcId, Location spawnLoc) {
        this.npcId = npcId;
        this.spawnLoc = spawnLoc;
    }

    public void tick() {
        // If the entity is dead, not valid, or not alive, spawn a new one
        if((nmsEntity == null || !nmsEntity.valid || !nmsEntity.isAlive())) {
            // Increase spawn timer if entity is dead
            if(spawnTimer < SPAWN_DELAY) {
                spawnTimer++;
            }
            // Respawn entity if spawn delay has been reached
            if(spawnTimer == SPAWN_DELAY) {
                nmsEntity = EntityHandler.instance.spawnEntity(spawnLoc, npcId).nmsEntity;
                spawnTimer = 0;
            }
        }
        else {
            spawnTimer = 0;
        }
    }
}
