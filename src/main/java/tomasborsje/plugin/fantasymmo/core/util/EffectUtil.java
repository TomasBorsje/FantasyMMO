package tomasborsje.plugin.fantasymmo.core.util;

import org.bukkit.Location;

public class EffectUtil {
    public static int PLAYER_HEIGHT = 2;
    public static int PLAYER_RADIUS = 1;

    public static Location getPointAroundPlayer(Location loc) {
        double yOffset = Math.random() * PLAYER_HEIGHT;
        double xOffset = (Math.random()-0.5) * PLAYER_RADIUS;
        double zOffset = (Math.random()-0.5) * PLAYER_RADIUS;
        return loc.clone().add(xOffset, yOffset, zOffset);
    }
}
