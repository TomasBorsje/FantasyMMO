package tomasborsje.plugin.fantasymmo.handlers;

import net.minecraft.world.phys.AABB;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.core.Region;

import java.util.ArrayList;

/**
 * Stores all the regions and their boundary boxes.
 */
public class RegionHandler {
    public static RegionHandler instance = new RegionHandler();
    private final ArrayList<Region> regions = new ArrayList<>();
    private final Region WILDERNESS = new Region("Wilderness", ChatColor.GREEN, 0, 1, 100);
    private RegionHandler() {
        regions.add(new Region("Verdant Bramble", ChatColor.GREEN, 2,1, 5, new AABB(1,0,-5, -65, 200, 54)));
        regions.add(new Region("Tutorial Valley", ChatColor.GREEN, 1,1, 5, new AABB(65,0,-50, -73, 200, 69)));

        // Sort by priority so higher priority regions are checked first
        regions.sort((o1, o2) -> o2.priority - o1.priority);
    }

    /**
     * Gets the region at the given location.
     * @param location The location.
     * @return The region at the given location.
     */
    public Region getRegion(Location location) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        for (Region region : regions) {
            if (region.contains(x, y, z)) {
                return region;
            }
        }
        return WILDERNESS;
    }
}