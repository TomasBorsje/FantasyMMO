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
    private final static int DEFAULT_LEASH_RANGE = 30*30; // 30 block range, squared so we don't need to sqrt
    private int spawnTimer = 0;
    private final String npcId;
    private CustomEntity entity;
    private final Location spawnLoc;
    private final int leashRange;
    private int leashTimer = 0;

    public NPCSpawner(String npcId, Location spawnLoc, int leashRange) {
        this.npcId = npcId;
        this.spawnLoc = spawnLoc;
        this.leashRange = leashRange*leashRange;
    }
    public NPCSpawner(String npcId, Location spawnLoc) {
        this.npcId = npcId;
        this.spawnLoc = spawnLoc;
        this.leashRange = DEFAULT_LEASH_RANGE;
    }

    public void tick() {
        LivingEntity nmsEntity = entity == null ? null : entity.nmsEntity;
        // If the entity is dead, not valid, or not alive, spawn a new one
        if((nmsEntity == null || !nmsEntity.valid || !nmsEntity.isAlive())) {
            // Increase spawn timer if entity is dead
            if(spawnTimer < SPAWN_DELAY) {
                spawnTimer++;
            }
            // Respawn entity if spawn delay has been reached
            if(spawnTimer == SPAWN_DELAY) {
                entity = EntityHandler.instance.spawnEntity(spawnLoc, npcId);
                spawnTimer = 0;
            }
        }
        else {
            spawnTimer = 0;
            leashTimer++;
            // Every 2 sec, If entity is out of leash range, teleport it back
            if(leashTimer == 40) {
                leashTimer = 0;
                if(nmsEntity.distanceToSqr(spawnLoc.getX(), spawnLoc.getY(), spawnLoc.getZ()) > leashRange) {
                    // Move back to spawn location so players can't kite them away
                    nmsEntity.setPos(spawnLoc.getX(), spawnLoc.getY(), spawnLoc.getZ());
                    nmsEntity.combatTracker.recheckStatus();
                    // Refill their health
                    entity.currentHealth = entity.maxHealth;
                }
            }
        }
    }
}
