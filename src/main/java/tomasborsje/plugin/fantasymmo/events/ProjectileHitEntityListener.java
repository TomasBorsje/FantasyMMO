package tomasborsje.plugin.fantasymmo.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrow;
import tomasborsje.plugin.fantasymmo.core.CustomEntity;
import tomasborsje.plugin.fantasymmo.handlers.EntityHandler;
import tomasborsje.plugin.fantasymmo.handlers.ProjectileHandler;

public class ProjectileHitEntityListener implements Listener {
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        // Get projectile
        Projectile proj = event.getEntity();

        // Only handle arrows
        if(proj instanceof Arrow arrowProj) {
            // Get the custom arrow
            AbstractCustomArrow arrow = ProjectileHandler.instance.getArrow(arrowProj);

            // Unhandled arrow
            if(arrow == null) {
                return;
            }

            // If we hit an entity
            if (event.getHitEntity() != null) {
                // Get custom entity that we hit
                CustomEntity entity = EntityHandler.instance.getEntity(event.getHitEntity().getEntityId());

                // Hit the entity
                arrow.onHitEntity(entity);
            }
            // Else if we hit a block
            else if (event.getHitBlock() != null) {
                arrow.onHitBlock();
            }
        }
    }
}
