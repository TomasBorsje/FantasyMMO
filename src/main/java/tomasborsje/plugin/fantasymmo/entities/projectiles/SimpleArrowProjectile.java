package tomasborsje.plugin.fantasymmo.entities.projectiles;

import org.bukkit.entity.Arrow;
import tomasborsje.plugin.fantasymmo.core.AbstractCustomArrowProjectile;
import tomasborsje.plugin.fantasymmo.core.PlayerData;

public class SimpleArrowProjectile extends AbstractCustomArrowProjectile {
    public SimpleArrowProjectile(Arrow arrowEntity, PlayerData player) {
        super(arrowEntity, player);
        this.damage = 3;
    }
}
