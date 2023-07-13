package tomasborsje.plugin.fantasymmo.content.projectiles;

import org.bukkit.entity.Arrow;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrowProjectile;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

public class SimpleArrowProjectile extends AbstractCustomArrowProjectile {
    public static final int BASE_DAMAGE = 3; // So outside classes can access this value
    public SimpleArrowProjectile(Arrow arrowEntity, PlayerData player) {
        super(arrowEntity, player);
        this.damage = BASE_DAMAGE;
    }
}
