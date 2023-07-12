package tomasborsje.plugin.fantasymmo.handlers;

import org.bukkit.entity.Arrow;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrowProjectile;
import tomasborsje.plugin.fantasymmo.core.CustomProjectile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores custom projectiles and custom arrows.
 */
public class ProjectileHandler {
    public static ProjectileHandler instance = new ProjectileHandler();
    private ProjectileHandler() {}
    private final ArrayList<CustomProjectile> projectiles = new ArrayList<>();
    private final HashMap<Integer, AbstractCustomArrowProjectile> arrows = new HashMap<>();

    public void tick() {
        // Iterate through projectiles with index
        for(int i = 0; i < projectiles.size(); i++) {
            CustomProjectile projectile = projectiles.get(i);
            // Tick projectile
            projectile.tick();

            // Delete if it's dead
            if(!projectile.alive) {
                projectiles.remove(i);
                i--;
            }
        }
    }

    public void registerArrow(Arrow arrow, AbstractCustomArrowProjectile customArrow) {
        arrows.put(arrow.getEntityId(), customArrow);
    }

    public void unregisterArrow(AbstractCustomArrowProjectile arrow) {
        arrows.remove(arrow.arrowEntity.getEntityId());
    }

    public AbstractCustomArrowProjectile getArrow(Arrow arrow) {
        return arrows.get(arrow.getEntityId());
    }

    public AbstractCustomArrowProjectile getArrow(int id) {
        return arrows.get(id);
    }

    public void spawnProjectile(CustomProjectile projectile) {
        projectiles.add(projectile);
    }
}
