package tomasborsje.plugin.fantasymmo.core.util;

import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftChatMessage;
import org.bukkit.util.Vector;
import tomasborsje.plugin.fantasymmo.FantasyMMO;
import tomasborsje.plugin.fantasymmo.core.enums.CustomDamageType;

public class HologramUtil {
    public static ArmorStand SpawnHologram(Location location, String message) {
        Level level = ((CraftWorld)location.getWorld()).getHandle();
        ArmorStand as = new ArmorStand(level, location.getX(), location.getY(), location.getZ());
        as.setMarker(true);
        as.setInvisible(true);
        as.setCustomNameVisible(true);
        as.setCustomName(CraftChatMessage.fromJSONOrString(message));
        level.addFreshEntity(as);
        return as;
    }

    /**
     * Spawns a hologram (floating text) at the given location for `duration` ticks.
     * @param location The location to center the text at.
     * @param message The text to display.
     * @param duration Number of ticks to display for.
     */
    public static void SpawnTemporaryHologram(Location location, String message, long duration) {
        ArmorStand armorStand = SpawnHologram(location, message);
        Bukkit.getScheduler().runTaskLater(FantasyMMO.Plugin, armorStand::kill, duration);
    }

    public static void SpawnDamageIndicator(Location location, float radius, CustomDamageType type, int damage) {
        double randomAngle = Math.random()*Math.PI*2;
        Vector offset = new Vector(Math.cos(randomAngle)*radius, Math.random()/2f-0.25f, Math.sin(randomAngle)*radius);
        SpawnTemporaryHologram(location, type.getDamageColor() + Integer.toString(damage), 20);
    }
}
