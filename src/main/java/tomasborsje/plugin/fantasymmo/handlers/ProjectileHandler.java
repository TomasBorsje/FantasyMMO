package tomasborsje.plugin.fantasymmo.handlers;

import tomasborsje.plugin.fantasymmo.core.CustomProjectile;

import java.util.ArrayList;

public class ProjectileHandler {
    public static ProjectileHandler instance = new ProjectileHandler();
    private ProjectileHandler() {}
    private final ArrayList<CustomProjectile> projectiles = new ArrayList<>();

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

    public void spawnProjectile(CustomProjectile projectile) {
        projectiles.add(projectile);
    }
}
