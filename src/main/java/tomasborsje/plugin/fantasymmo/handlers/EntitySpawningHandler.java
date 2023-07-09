package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.Location;
import org.bukkit.World;
import tomasborsje.plugin.fantasymmo.core.EntitySpawnPoint;

import java.util.ArrayList;

public class EntitySpawningHandler {

    public static EntitySpawningHandler instance = new EntitySpawningHandler();
    ArrayList<EntitySpawnPoint> spawners = new ArrayList<>();

    private EntitySpawningHandler() { }

    public void tick(World world) {
        for(EntitySpawnPoint spawner : spawners) {
            spawner.tick();
        }
    }

    /**
     * Loads all the entity spawners, so they can be ticked.
     * @param world The world to load the spawners into.
     */
    public void init(World world) {
        spawners.add(new EntitySpawnPoint("FOREST_SLIME", new Location(world, 141.5, 69, -119.5)));
        spawners.add(new EntitySpawnPoint("FOREST_SLIME", new Location(world, 140.5, 67, -132.5)));
        spawners.add(new EntitySpawnPoint("FOREST_SLIME", new Location(world, 145.5, 67, -140.5)));
        spawners.add(new EntitySpawnPoint("FOREST_SLIME", new Location(world, 152.5, 66, -148.5)));
        spawners.add(new EntitySpawnPoint("FOREST_SLIME", new Location(world, 147.5, 70, -124.5)));
    }
}
