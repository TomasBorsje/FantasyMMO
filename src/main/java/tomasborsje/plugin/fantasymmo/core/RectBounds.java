package tomasborsje.plugin.fantasymmo.core;

import org.bukkit.util.Vector;

public class RectBounds {
    public Vector position;
    public final int width;
    public final int height;
    public final int depth;
    public RectBounds(Vector position, int width, int height, int depth) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
    public boolean isInBounds(Vector position) {
        return position.getX() >= this.position.getX() && position.getX() <= this.position.getX() + width &&
                position.getY() >= this.position.getY() && position.getY() <= this.position.getY() + height &&
                position.getZ() >= this.position.getZ() && position.getZ() <= this.position.getZ() + depth;
    }
}
