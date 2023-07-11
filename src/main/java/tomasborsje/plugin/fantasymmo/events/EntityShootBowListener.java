package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrow;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.entities.projectiles.SimpleArrow;
import tomasborsje.plugin.fantasymmo.handlers.PlayerHandler;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;

public class EntityShootBowListener implements Listener {
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        // Only handle players firing arrows
        if(event.getEntity() instanceof Player player) {

            // Get player data
            PlayerData playerData = PlayerHandler.instance.getPlayerData(player);

            // If an arrow was fired, get which type of arrow it is and register it with the projectile handler
            if (event.getProjectile() instanceof Arrow arrow) {
                // TODO: Get arrow type based on held bow item
                AbstractCustomArrow customArrow = new SimpleArrow(arrow, playerData);

                // Register arrow with projectile handler
                ProjectileHandler.instance.registerArrow(arrow, customArrow);
            }
        }
    }
}
