package tomasborsje.plugin.fantasymmo.core;

import net.minecraft.world.phys.AABB;
import org.bukkit.ChatColor;

/**
 * A region within the game world.
 * Regions are used to determine what area the player is in (displayed on scoreboard).
 */
public class Region {
    public final String name;
    public final int minLevel;
    public final int maxLevel;
    public final int priority;
    public final ChatColor color;
    private final AABB[] boxes;

    public Region(String name, ChatColor color, int priority, int minLevel, int maxLevel, AABB... boxes) {
        this.name = name;
        this.color = color;
        this.priority = priority;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.boxes = boxes;
    }

    /**
     * Checks if the given position is inside this region.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param z The z coordinate.
     * @return True if the position is inside this region.
     */
    public boolean contains(double x, double y, double z) {
        for (AABB box : boxes) {
            if (box.contains(x, y, z)) {
                return true;
            }
        }
        return false;
    }

    public String getDisplayName() {
        return color + name;
    }

    public String getLevelDisplay() {
        return ChatColor.GRAY + "Level " + minLevel + " - " + maxLevel;
    }
}
