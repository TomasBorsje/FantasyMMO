package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.Location;
import org.bukkit.World;
import tomasborsje.plugin.fantasymmo.core.NPCSpawner;

import java.util.ArrayList;

public class EntitySpawningHandler {

    public static EntitySpawningHandler instance = new EntitySpawningHandler();
    ArrayList<NPCSpawner> spawners = new ArrayList<>();
    boolean initialized = false;

    private EntitySpawningHandler() { }

    public void tick(World world) {
        if(!initialized) {
            init(world);
        }
        for(NPCSpawner spawner : spawners) {
            spawner.tick();
        }
    }

    private void init(World world) {
        initialized = true;
        spawners.add(new NPCSpawner("FOREST_SLIME", new Location(world, 132, 67, -19)));
    }
}
