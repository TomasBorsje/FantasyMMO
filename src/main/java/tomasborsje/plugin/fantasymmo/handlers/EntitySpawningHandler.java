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
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -19.5, 90, 43.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -29.5, 90, 33.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -16.5, 90, 31.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -22.5, 90, 19.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -34.5, 90, 10.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -46.5, 90, 12.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -49.5, 90, 26.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -48.5, 90, 36.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -40.5, 90, 40.5)));
        spawners.add(new EntitySpawnPoint("BRAMBLE_SLIME", new Location(world, -38.5, 90, 25.5)));
    }
}
