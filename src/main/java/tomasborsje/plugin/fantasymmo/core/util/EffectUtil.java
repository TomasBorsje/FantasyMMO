package tomasborsje.plugin.fantasymmo.core.util;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class EffectUtil {
    public static int PLAYER_HEIGHT = 2;
    public static int PLAYER_RADIUS = 1;

    public static Location getPointAroundPlayer(Location loc) {
        double yOffset = Math.random() * PLAYER_HEIGHT;
        double xOffset = (Math.random()-0.5) * PLAYER_RADIUS;
        double zOffset = (Math.random()-0.5) * PLAYER_RADIUS;
        return loc.clone().add(xOffset, yOffset, zOffset);
    }

    public static void SpawnColoredRedstoneAroundPlayer(Player player, Color color) {
        player.getWorld().spawnParticle(Particle.REDSTONE, EffectUtil.getPointAroundPlayer(player.getLocation()),
                0, 1, 0, 0, 0, new Particle.DustOptions(color, 0.7f));
    }

    public static void SpawnColoredSwirlAroundPlayer(Player player, Color color) {
        player.getWorld().spawnParticle(Particle.SPELL_MOB, EffectUtil.getPointAroundPlayer(player.getLocation()), 0,
                color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 1);
    }

    public static void SpawnParticleAroundPlayer(Player player, Particle p) {
        player.getWorld().spawnParticle(p, EffectUtil.getPointAroundPlayer(player.getLocation()),1);
    }
}
