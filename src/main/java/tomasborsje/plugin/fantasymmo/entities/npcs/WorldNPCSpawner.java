package tomasborsje.plugin.fantasymmo.entities.npcs;

import org.bukkit.World;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;

import java.util.ArrayList;

/**
 * Stores the locations and IDs of all NPCs that are always present in the world.
 * This is used to spawn them when the server starts.
 */
public class WorldNPCSpawner {
    public static final ArrayList<NPCSpawnPoint> worldNPCs = new ArrayList<>(1);

    /* Starter Zone */
    static {
        worldNPCs.add(new NPCSpawnPoint(new Vector(110.5,70,-128.5), "TEST_NPC"));
    }

    public static void init(World world) {
        // Spawn each NPC into the world
        for(NPCSpawnPoint spawnPoint : worldNPCs) {
            EntityHandler.instance.spawnNPC(spawnPoint.position.toLocation(world), spawnPoint.npcId);
        }
    }
}

